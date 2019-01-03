package com.github.gamgoon.concurrency.ch03.command;

import com.github.gamgoon.concurrency.ch03.dao.WDIDAO;

public class ReportCommand extends Command {

    public ReportCommand(String[] command) {
        super(command);
    }

    @Override
    public String execute() {
        WDIDAO dao = WDIDAO.getDAO();
        return dao.report(command[1]);
    }
}
