package com.github.gamgoon.concurrency.ch04.command;

import com.github.gamgoon.concurrency.ch03.cache.ParallelCache;
import com.github.gamgoon.concurrency.ch03.command.Command;
import com.github.gamgoon.concurrency.ch03.logger.Logger;
import com.github.gamgoon.concurrency.ch03.server.ConcurrentServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class ConcurrentCommand extends Command implements Comparable<ConcurrentCommand>, Runnable {
    private String username;
    private byte priority;
    private Socket socket;

    public ConcurrentCommand(Socket socket, String[] command) {
        super(command);
        username = command[1];
        priority = Byte.parseByte(command[2]);
        this.socket = socket;
    }

    @Override
    public abstract String execute();

    @Override
    public int compareTo(ConcurrentCommand o) {
        return Byte.compare(o.getPriority(), this.getPriority());
    }

    @Override
    public void run() {
        String message = "Running a Task: Username: " + username + " Priority: " + priority;
        Logger.sendMessage(message);
        String ret = execute();
        ParallelCache cache = ConcurrentServer.getCache();
        if (isCacheable()) {
            cache.put(String.join(";", command), ret);
        }
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);) {
            System.out.println(ret);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(ret);

    }

    public String getUsername() {
        return username;
    }

    public byte getPriority() {
        return priority;
    }

    public Socket getSocket() {
        return socket;
    }
}
