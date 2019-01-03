package com.github.gamgoon.concurrency.ch03.command;

public abstract class Command {
    protected final String[] command;

    public Command(String[] command) {
        this.command = command;
    }

    public abstract String execute();
}
