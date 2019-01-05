package com.github.gamgoon.concurrency.ch04.command;

import com.github.gamgoon.concurrency.ch03.command.Command;

import java.net.Socket;

public class ConcurrentErrorCommand extends ConcurrentCommand {

    public ConcurrentErrorCommand(Socket socket, String[] command) {
        super(socket, command);
    }

    @Override
    public String execute() {
        return "Unknown command: " + command[0];
    }
}
