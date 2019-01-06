package com.github.gamgoon.concurrency.ch05.secondexample;

import java.util.Map;

public class Document {
    private String filename;
    private Map<String, Integer> vocabulary;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Map<String, Integer> getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(Map<String, Integer> vocabulary) {
        this.vocabulary = vocabulary;
    }
}
