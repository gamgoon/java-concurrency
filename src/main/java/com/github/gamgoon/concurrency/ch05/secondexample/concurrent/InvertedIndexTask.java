package com.github.gamgoon.concurrency.ch05.secondexample.concurrent;

import com.github.gamgoon.concurrency.ch05.secondexample.Document;

import java.util.Map;
import java.util.concurrent.*;

public class InvertedIndexTask implements Runnable {
    private CompletionService<Document> completionService;
    private ConcurrentHashMap<String, ConcurrentLinkedDeque<String>> invertedIndex;

    public InvertedIndexTask(CompletionService<Document> completionService,
                             ConcurrentHashMap<String, ConcurrentLinkedDeque<String>> invertedIndex) {
        this.completionService = completionService;
        this.invertedIndex = invertedIndex;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                try {
                    Document document = completionService.take().get();
                    updateInvertedIndex(document.getVocabulary(), invertedIndex, document.getFilename());
                } catch (InterruptedException e) {
                    break;
                }
            }
            while (true) {
                Future<Document> future = completionService.poll();
                if (future == null) {
                    break;
                }
                Document document = future.get();
                updateInvertedIndex(document.getVocabulary(), invertedIndex, document.getFilename());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void updateInvertedIndex(Map<String, Integer> voc,
                                     ConcurrentHashMap<String, ConcurrentLinkedDeque<String>> invertedIndex,
                                     String filename) {
        for (String word : voc.keySet()) {
            if (word.length() >= 3) {
                invertedIndex.computeIfAbsent(word, k -> new ConcurrentLinkedDeque<>()).add(filename);
            }
        }
    }
}
