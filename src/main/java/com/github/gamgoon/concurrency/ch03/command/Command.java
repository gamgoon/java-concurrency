package com.github.gamgoon.concurrency.ch03.command;

public abstract class Command {
    protected final String[] command;
    private boolean cacheable;

    public Command(String[] command) {
        this.command = command;
        setCacheable(true);
    }

    public abstract String execute();

    public boolean isCacheable() {
        return cacheable;
    }

    public void setCacheable(boolean cacheable) {
        this.cacheable = cacheable;
    }
}
