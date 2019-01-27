package com.github.gamgoon.corejava;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.stream.LongStream;

public class Ex09 {
    public static void main(String[] args) throws InterruptedException {
        LongAccumulator accumulator = new LongAccumulator(Long::max, 0);
        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        LongStream.range(0, 1000).forEach(i -> service.submit(() -> accumulator.accumulate(i)));
        service.shutdown();
        service.awaitTermination(1, TimeUnit.DAYS);
        long result = accumulator.get();
        System.out.println(result);
    }
}
