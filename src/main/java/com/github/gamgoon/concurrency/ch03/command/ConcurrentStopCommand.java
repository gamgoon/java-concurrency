package com.github.gamgoon.concurrency.ch03.command;

import com.github.gamgoon.concurrency.ch03.server.ConcurrentServer;

public class ConcurrentStopCommand extends Command {
    public ConcurrentStopCommand(String[] command) {
        super(command);
    }

    @Override
    public String execute() {
        ConcurrentServer.shutdown();
        return "Server stopped";
    }
}
