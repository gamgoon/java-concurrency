package com.github.gamgoon.concurrency.ch03.command;

public abstract class ConcurrentCommand {
    protected final String[] command;

    public ConcurrentCommand(String[] command) {
        this.command = command;
    }

    public abstract String execute();
}
