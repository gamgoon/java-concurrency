package com.github.gamgoon.concurrency.ch05.secondexample.concurrent;

import com.github.gamgoon.concurrency.ch05.secondexample.Document;
import com.github.gamgoon.concurrency.ch05.secondexample.DocumentParser;

import java.io.File;
import java.util.Map;
import java.util.concurrent.Callable;

public class IndexingTask implements Callable<Document> {
    private File file;

    public IndexingTask(File file) {
        this.file = file;
    }

    @Override
    public Document call() throws Exception {
        DocumentParser parser = new DocumentParser();
        Map<String, Integer> voc = parser.parse(file.getAbsolutePath());
        Document doc = new Document();
        doc.setFilename(file.getName());
        doc.setVocabulary(voc);
        return doc;
    }
}
