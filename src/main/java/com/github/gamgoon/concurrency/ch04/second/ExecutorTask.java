package com.github.gamgoon.concurrency.ch04.second;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ExecutorTask<V> extends FutureTask<V> implements RunnableScheduledFuture<V> {
    private RunnableScheduledFuture<V> task;
    private NewsExecutor executor;
    private long startDate;
    private String name;

    public ExecutorTask(Runnable runnable, V result, RunnableScheduledFuture<V> task, NewsExecutor executor) {
        super(runnable, result);
        this.task = task;
        this.executor = executor;
        this.startDate = new Date().getTime();
        this.name = ((NewsTask) runnable).getName();
    }

    @Override
    public boolean isPeriodic() {
        return task.isPeriodic();
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long delay;
        if (!isPeriodic()) {
            delay = task.getDelay(unit);
        } else {
            if (startDate == 0) {
                delay = task.getDelay(unit);
            } else {
                Date now = new Date();
                delay = startDate - now.getTime();
                delay = unit.convert(delay, TimeUnit.MILLISECONDS);
            }
        }
        return delay;
    }

    @Override
    public int compareTo(Delayed o) {
        return Long.compare(this.getStartDate(), ((ExecutorTask<V>)o).getStartDate());
    }

    private long getStartDate() {
        return startDate;
    }

    @Override
    public void run() {
        if (isPeriodic() && (!executor.isShutdown())) {
            super.runAndReset();
            Date now = new Date();
            startDate = now.getTime() + Timer.getPeriod();
            executor.getQueue().add(this);
            System.out.println("Start Date: " + new Date(startDate));
        }
    }
}
