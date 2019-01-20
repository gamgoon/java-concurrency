package com.github.gamgoon.cookbook.ch05.async;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ThreadLocalRandom;

public class FolderProcessor extends CountedCompleter<List<String>> {
    private String path;
    private String extension;
    private List<FolderProcessor> tasks;
    private List<String> resultList = new ArrayList<>();
    private int myIndex;

    public FolderProcessor(CountedCompleter<?> completer,
                           String path, String extension) {
        super(completer);
        this.path = path;
        this.extension = extension;
        this.myIndex = ThreadLocalRandom.current().nextInt(100000);
    }

    public FolderProcessor(String path, String extension) {
        this.path = path;
        this.extension = extension;
    }

    @Override
    public void compute() {
        resultList = new ArrayList<>();
        tasks = new ArrayList<>();

        File file = new File(path);
        File[] content = file.listFiles();

        if (content != null) {
            for (int i = 0; i < content.length; i++) {
                if (content[i].isDirectory()) {
                    System.out.printf("%s - %d: fork\n", Thread.currentThread().getName(), this.myIndex);
                    FolderProcessor task = new FolderProcessor(this, content[i].getAbsolutePath(), extension);
                    task.fork();
                    addToPendingCount(1);
                    tasks.add(task);
                } else {
                    if (checkFile(content[i].getName())) {
                        System.out.printf("%s - %d: file is %s\n", Thread.currentThread().getName(), this.myIndex, content[i].getAbsolutePath());
                        resultList.add(content[i].getAbsolutePath());
                    }
                }
            }
            if (tasks.size() > 50) {
                System.out.printf("%s - %d: %d tasks ran.\n", file.getAbsolutePath(), this.myIndex, tasks.size());
            }
        }
        System.out.printf("%s - %d: tryComplete\n", Thread.currentThread().getName(), this.myIndex);
        tryComplete();
    }

    @Override
    public void onCompletion(CountedCompleter caller) {
        System.out.printf("%s - %d: onCompletion start \n", Thread.currentThread().getName(), this.myIndex);
        for (FolderProcessor childTask : tasks) {
            System.out.printf("%s - %d: count is %d\n", Thread.currentThread().getName(), this.myIndex, childTask.getResultList().size());
            resultList.addAll(childTask.getResultList());
        }
        System.out.printf("%s - %d: onCompletion end\n", Thread.currentThread().getName(), this.myIndex);
    }

    private boolean checkFile(String name) {
//        return name.endsWith(extension);
        return true;
    }

    @Override
    public List<String> getRawResult() {
        System.out.printf("%s - %d: getRawResult \n", Thread.currentThread().getName(), this.myIndex);
        return resultList;
    }

    public List<String> getResultList() {
        System.out.printf("%s - %d: getResultList \n", Thread.currentThread().getName(), this.myIndex);
        return resultList;
    }
}
