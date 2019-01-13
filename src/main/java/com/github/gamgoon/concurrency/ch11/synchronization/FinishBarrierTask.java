package com.github.gamgoon.concurrency.ch11.synchronization;

public class FinishBarrierTask implements Runnable {
    @Override
    public void run() {
        System.out.println("FinishBarrierTask: All the tasks have finished");
    }
}
