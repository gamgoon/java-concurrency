package com.github.gamgoon.concurrency.ch03.command;

public class ConcurrentErrorCommand extends Command {
    public ConcurrentErrorCommand(String[] command) {
        super(command);
    }

    @Override
    public String execute() {
        return "Unknown command: " + command[0];
    }
}
