package com.github.gamgoon.concurrency.ch04.command;

import com.github.gamgoon.concurrency.ch03.command.Command;
import com.github.gamgoon.concurrency.ch03.server.ConcurrentServer;

import java.net.Socket;

public class ConcurrentStopCommand extends ConcurrentCommand {

    public ConcurrentStopCommand(Socket socket, String[] command) {
        super(socket, command);
    }

    @Override
    public String execute() {
        ConcurrentServer.shutdown();
        return "Server stopped";
    }
}
