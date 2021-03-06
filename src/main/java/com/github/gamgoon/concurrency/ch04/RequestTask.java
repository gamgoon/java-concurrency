package com.github.gamgoon.concurrency.ch04;

import com.github.gamgoon.concurrency.ch03.cache.ParallelCache;
import com.github.gamgoon.concurrency.ch03.logger.Logger;
import com.github.gamgoon.concurrency.ch03.server.ConcurrentServer;
import com.github.gamgoon.concurrency.ch04.command.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class RequestTask implements Runnable {
    private LinkedBlockingQueue<Socket> pendingConnections;
    private ServerExecutor executor = new ServerExecutor();
    private ConcurrentMap<String, ConcurrentMap<ConcurrentCommand, ServerTask<?>>> taskController;

    public RequestTask(LinkedBlockingQueue<Socket> pendingConnections,
                       ConcurrentMap<String, ConcurrentMap<ConcurrentCommand, ServerTask<?>>> taskController) {
        this.pendingConnections = pendingConnections;
        this.taskController = taskController;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().interrupted()) {
                try {
                    Socket clientSocket = pendingConnections.take();
                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(clientSocket.getInputStream()));
                    String line = in.readLine();
                    Logger.sendMessage(line);
                    ConcurrentCommand command;
                    ParallelCache cache = ConcurrentServer.getCache();
                    String ret = cache.get(line);
                    if (ret == null) {
                        String[] commandData = line.split(";");
                        System.out.println("Command: " + commandData[0]);
                        switch (commandData[0]) {
                            case "q":
                                System.out.println("Query");
                                command = new ConcurrentQueryCommand(clientSocket,
                                        commandData);
                                break;
                            case "r":
                                System.out.println("Report");
                                command = new ConcurrentReportCommand(clientSocket,
                                        commandData);
                                break;
                            case "s":
                                System.out.println("Status");
                                command = new ConcurrentStatusCommand(executor, clientSocket, commandData);
                                break;
                            case "z":
                                System.out.println("Stop");
                                command = new ConcurrentStopCommand(clientSocket, commandData);
                                break;
                            case "c":
                                System.out.println("Cancel");
                                command = new ConcurrentCancelCommand(clientSocket,
                                        commandData);
                                break;
                            default:
                                System.out.println("Error");
                                command = new ConcurrentErrorCommand(clientSocket, commandData);
                                break;
                        }
                        ServerTask<?> controller = (ServerTask<?>) executor.submit(command);
                        storeController(command.getUsername(), controller, command);
                    } else {
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        System.out.println(ret);
                        clientSocket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
// No Action Required
        }
    }

    private void storeController(String userName, ServerTask<?>controller, ConcurrentCommand command) {
        taskController.computeIfAbsent(userName, k -> new ConcurrentHashMap<>()).put(command, controller);
    }

    public void shutdown() {
        String message="Request Task: "+pendingConnections.size()+" pending connections.";
        Logger.sendMessage(message);
        executor.shutdown();
    }
    public void terminate() {
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
            executor.writeStatistics();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}