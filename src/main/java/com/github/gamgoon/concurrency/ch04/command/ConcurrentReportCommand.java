package com.github.gamgoon.concurrency.ch04.command;

import com.github.gamgoon.concurrency.ch03.dao.WDIDAO;

import java.net.Socket;

public class ConcurrentReportCommand extends ConcurrentCommand {

    public ConcurrentReportCommand(Socket socket, String[] command) {
        super(socket, command);
    }

    @Override
    public String execute() {
        WDIDAO dao = WDIDAO.getDAO();
        return dao.report(command[3]);
    }
}
