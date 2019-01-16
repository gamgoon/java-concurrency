package com.github.gamgoon.cookbook.ch02.condition;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        FileMock mock = new FileMock(100, 10);
        Buffer buffer = new Buffer(20);
        Producer producer = new Producer(mock, buffer);
        Thread producerThread = new Thread(producer, "Producer");

        Consumer[] consumers = new Consumer[3];
        Thread[] consumersThreads = new Thread[3];

        for (int i = 0; i < 3; i++) {
            consumers[i] = new Consumer(buffer);
            consumersThreads[i] = new Thread(consumers[i], "Consumer " + i);
        }
        producerThread.start();
        Thread.sleep(10000);
        for (int i = 0; i < 3; i++) {
            consumersThreads[i].start();
        }
    }
}
