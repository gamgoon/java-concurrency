package com.github.gamgoon.concurrency.ch03.command;

import com.github.gamgoon.concurrency.ch03.logger.Logger;
import com.github.gamgoon.concurrency.ch03.server.ConcurrentServer;

import java.util.concurrent.ThreadPoolExecutor;

public class ConcurrentStatusCommand extends Command {
    public ConcurrentStatusCommand(String[] command) {
        super(command);
//        setCacheable(false);
    }

    @Override
    public String execute() {
    StringBuilder sb = new StringBuilder();
        ThreadPoolExecutor executor = ConcurrentServer.getExecutor();
        Logger.sendMessage(sb.toString());
        return sb.toString();
    }
}
