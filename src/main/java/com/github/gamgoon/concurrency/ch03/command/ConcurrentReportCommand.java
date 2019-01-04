package com.github.gamgoon.concurrency.ch03.command;

import com.github.gamgoon.concurrency.ch03.dao.WDIDAO;

public class ConcurrentReportCommand extends Command {
    public ConcurrentReportCommand(String[] command) {
        super(command);
    }

    @Override
    public String execute() {
        WDIDAO dao = WDIDAO.getDAO();
        return dao.report(command[1]);
    }
}
