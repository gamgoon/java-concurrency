package com.github.gamgoon.concurrency.ch05.secondexample.concurrent;

import com.github.gamgoon.concurrency.ch05.secondexample.Document;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class MultipleConcurrentIndexing {
    public static void main(String[] args) {
        String filePath = "/Users/gamgoon/Downloads/9781785887949_Code/B05875_MasteringConcurrencyProgrammingWithJava9SecondEdition_Code/Chapter05/TextIndexing/data";
        int numCores = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Math.max(numCores - 1, 1));
        ExecutorCompletionService<List<Document>> completionService = new ExecutorCompletionService<>(executor);
        ConcurrentHashMap<String, ConcurrentLinkedDeque<String>> invertedIndex = new ConcurrentHashMap<>();
        Date start, end;
        File source = new File(filePath);
        File[] files = source.listFiles();

        int NUMBER_OF_TASKS = 5000; // 100, 1000, 5000

        start = new Date();
        List<File> taskFiles = new ArrayList<>();
        for (File file : files) {
            taskFiles.add(file);
            if (taskFiles.size() == NUMBER_OF_TASKS) {
                MultipleIndexingTask task = new MultipleIndexingTask(taskFiles);
                completionService.submit(task);
                taskFiles = new ArrayList<>();
                if (executor.getQueue().size() > 10) {
                    do {
                        try {
                            TimeUnit.MILLISECONDS.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } while (executor.getQueue().size() > 10);
                }
            }
        }
        if (taskFiles.size() > 0) {
            MultipleIndexingTask task = new MultipleIndexingTask(taskFiles);
            completionService.submit(task);
        }


        MultipleInvertedIndexTask invertedIndexTask = new MultipleInvertedIndexTask(completionService, invertedIndex);
        Thread thread1 = new Thread(invertedIndexTask);
        thread1.start();
        MultipleInvertedIndexTask invertedIndexTask2 = new MultipleInvertedIndexTask(completionService, invertedIndex);
        Thread thread2 = new Thread(invertedIndexTask2);
        thread2.start();

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
            thread1.interrupt();
            thread2.interrupt();
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        end = new Date();
        System.out.println("Execution Time: " + (end.getTime() - start.getTime()));
        System.out.println("invertedIndex: " + invertedIndex.size());
    }
}
