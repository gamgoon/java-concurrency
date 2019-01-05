package com.github.gamgoon.concurrency.ch04;

import com.github.gamgoon.concurrency.ch03.logger.Logger;
import com.github.gamgoon.concurrency.ch04.command.ConcurrentCommand;
import com.github.gamgoon.concurrency.ch04.server.ConcurrentServer;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.*;

public class ServerExecutor extends ThreadPoolExecutor {
    private static int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static int MAXIMUM_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static long KEEP_ALIVE_TIME = 10;
    private static RejectedTaskController REJECTED_TASK_CONTROLLER = new RejectedTaskController();
    private ConcurrentHashMap<Runnable, Date> startTimes;
    private ConcurrentHashMap<String, ExecutorStatistics> executionStatistics;

    public ServerExecutor() {
        super(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME,
                TimeUnit.SECONDS, new PriorityBlockingQueue<>(), REJECTED_TASK_CONTROLLER);
        startTimes = new ConcurrentHashMap<>();
        executionStatistics = new ConcurrentHashMap<>();
    }

    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        startTimes.put(r, new Date());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        ServerTask<?> task = (ServerTask<?>) r;
        ConcurrentCommand command = task.getCommand();
        if (t == null) {
            if (!task.isCancelled()) {
                Date startDate = startTimes.remove(r);
                Date endDate = new Date();
                long executionTime = endDate.getTime() -
                        startDate.getTime();
                ExecutorStatistics statistics = executionStatistics
                        .computeIfAbsent(command.getUsername(),
                                n -> new ExecutorStatistics());
                statistics.addExecutionTime(executionTime);
                statistics.addTask();
                ConcurrentServer.finishTask(command.getUsername(),
                        command);
            } else {
                String message = "The task" + command.hashCode() + "of user " + command.getUsername() +
                        " has been cancelled.";
                Logger.sendMessage(message);
            }
        } else {
            String message = "The exception " + t.getMessage() + " has been thrown.";
            Logger.sendMessage(message);
        }
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
        return (ServerTask<T>) runnable;
    }

    public void writeStatistics() {
        for (Map.Entry<String, ExecutorStatistics> entry : executionStatistics
                .entrySet()) {
            String user = entry.getKey();
            ExecutorStatistics stats = entry.getValue();
            Logger.sendMessage(user + ":" + stats);
        }
    }
}
