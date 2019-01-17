package com.github.gamgoon.cookbook.ch03.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class SeedGenerator implements Runnable {
    private CompletableFuture<Integer> resultCommunicator;

    public SeedGenerator(CompletableFuture<Integer> resultCommunicator) {
        this.resultCommunicator = resultCommunicator;
    }

    @Override
    public void run() {
        System.out.printf("SeedGenerator: Generating seed...\n");
        // wait 5 seconds
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int seed = (int) Math.rint(Math.random() * 10);
        System.out.printf("SeedGenerator: Seed generated: %d\n", seed);
        resultCommunicator.complete(seed);
    }
}
