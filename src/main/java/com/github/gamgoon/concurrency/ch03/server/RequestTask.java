package com.github.gamgoon.concurrency.ch03.server;

import com.github.gamgoon.concurrency.ch03.Constants;
import com.github.gamgoon.concurrency.ch03.cache.ParallelCache;
import com.github.gamgoon.concurrency.ch03.command.*;
import com.github.gamgoon.concurrency.ch03.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class RequestTask implements Runnable {
    private final Socket clientSocket;

    public RequestTask(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {

            String line = in.readLine();
            Logger.sendMessage(line);
            ParallelCache cache = ConcurrentServer.getCache();
            String ret = cache.get(line);

            if (ret == null) {
                Command command;
                String[] commandData = line.split(";");
                System.out.println("Command: " + commandData[0]);
                switch (commandData[0]) {
                    case "q":
                        System.out.println("Query");
                        command = new ConcurrentQueryCommand(commandData);
                        break;
                    case "r":
                        System.out.println("Report");
                        command = new ConcurrentReportCommand(commandData);
                        break;
                    case "s":
                        System.out.println("Status");
                        command = new ConcurrentStatusCommand(commandData);
                        break;
                    case "z":
                        System.out.println("Stop");
                        command = new ConcurrentStopCommand(commandData);
                        break;
                    default:
                        System.out.println("Error");
                        command = new ConcurrentErrorCommand(commandData);
                        break;
                }
                String response = command.execute();
                System.out.println(response);
                cache.put(line, response);
                out.write(response);
            } else {
                Logger.sendMessage("Command " + line + " was found in the cache");
            }

//            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
