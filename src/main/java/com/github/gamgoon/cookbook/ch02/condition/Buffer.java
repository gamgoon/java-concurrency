package com.github.gamgoon.cookbook.ch02.condition;

import java.util.LinkedList;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private final LinkedList<String> buffer;
    private final int maxSize;
    private final ReentrantLock lock;
    private final Condition lines;
    private final Condition spaces;
    private boolean pendingLines;

    public Buffer(int maxSize) {
        this.maxSize = maxSize;
        buffer = new LinkedList<>();
        lock = new ReentrantLock();
        lines = lock.newCondition();
        spaces = lock.newCondition();
        pendingLines = true;
    }

    public void insert(String line) {
        lock.lock();
        try {
            System.out.printf("%s: insert lock", Thread.currentThread().getName());
            Thread.sleep(10000);
            while (buffer.size() == maxSize) {
                System.out.printf("%s: spaces.await()\n", Thread.currentThread().getName());
                spaces.await();
            }
            buffer.offer(line);
            System.out.printf("%s: Inserted Line: %d\n", Thread.currentThread().getName(), buffer.size());
            lines.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public String get() {
        String line = null;
        System.out.printf("%s: in get()\n", Thread.currentThread().getName());
        lock.lock();
        System.out.printf("%s: in lock\n", Thread.currentThread().getName());

        try {
            Thread.sleep(10000);
            while (buffer.size() == 0 && hasPendingLines()) {
                System.out.printf("%s: spaces.await()\n", Thread.currentThread().getName());
                lines.await();
            }
            if (hasPendingLines()) {
                line = buffer.poll();
                System.out.printf("%s: Line Readed: %d\n", Thread.currentThread().getName(), buffer.size());
                spaces.signalAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return line;
    }

    public synchronized boolean hasPendingLines() {
        System.out.printf("%s: hasPendingLines\n", Thread.currentThread().getName());
        return pendingLines || buffer.size() > 0;
    }

    public synchronized void setPendingLines(boolean pendingLines) {
        System.out.printf("%s: setPendingLines %s\n", Thread.currentThread().getName(), pendingLines);
        this.pendingLines = pendingLines;
    }
}
