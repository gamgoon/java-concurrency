package com.github.gamgoon.corejava;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

public class Ex08 {
    public static void main(String[] args) throws InterruptedException {
        AtomicLong total = new AtomicLong(0);
        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        long start = System.currentTimeMillis();
        IntStream.range(0, 1000)
                .forEach(i -> service.submit(() -> {
//                    System.out.printf("%s\n", Thread.currentThread().getName());
                    for (int j = 0; j < 1000_000; j++) {
                        total.incrementAndGet();
                    }
                }));
        service.shutdown();
        service.awaitTermination(1, TimeUnit.DAYS);
        System.out.printf("done. total is %d in %d\n", total.get(), System.currentTimeMillis() - start);

        LongAdder totalAdder = new LongAdder();
        ExecutorService service2 = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        long start2 = System.currentTimeMillis();
        IntStream.range(0, 1000)
                .forEach(i -> service2.submit(() -> {
//                    System.out.printf("%s\n", Thread.currentThread().getName());
                    for (int j = 0; j < 1000_000; j++) {
                        totalAdder.increment();
                    }
                }));
        service2.shutdown();
        service2.awaitTermination(1, TimeUnit.DAYS);
        System.out.printf("done. total is %d in %d\n", total.get(), System.currentTimeMillis() - start2);
    }
}
