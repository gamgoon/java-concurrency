package com.github.gamgoon.concurrency.ch03.logger;

import java.util.concurrent.TimeUnit;

public class LogTask implements Runnable {

    @Override
    public void run() {
        try {
            while (Thread.currentThread().interrupted()) {
                TimeUnit.SECONDS.sleep(10);
                Logger.writeLogs();
            }
        } catch (InterruptedException e) {
        }
        Logger.writeLogs();
    }
}
