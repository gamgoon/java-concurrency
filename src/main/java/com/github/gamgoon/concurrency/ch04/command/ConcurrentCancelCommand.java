package com.github.gamgoon.concurrency.ch04.command;

import com.github.gamgoon.concurrency.ch03.logger.Logger;
import com.github.gamgoon.concurrency.ch04.server.ConcurrentServer;

import java.net.Socket;

public class ConcurrentCancelCommand extends ConcurrentCommand {
    public ConcurrentCancelCommand(Socket socket, String[] command) {
        super(socket, command);
    }

    @Override
    public String execute() {
        ConcurrentServer.cancelTasks(getUsername());
        String message = "Tasks of user " + getUsername() + " has been cancelled.";
        Logger.sendMessage(message);
        return message;
    }
}
