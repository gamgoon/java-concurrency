package com.github.gamgoon.concurrency.ch03.command;

public class StopCommand extends Command {
    public StopCommand(String[] command) {
        super(command);
    }

    @Override
    public String execute() {
        return "Server stopped";
    }
}
