package com.github.gamgoon.concurrency.ch07.first;

public class Document {
    private Word[] data;
    private String name;
    private DocumentCluster cluster;

    public Document(String name, int size) {
        this.name = name;
        this.data = new Word[size];
        this.cluster = null;
    }

    public Word[] getData() {
        return data;
    }

    public void setData(Word[] data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DocumentCluster getCluster() {
        return cluster;
    }

    public boolean setCluster(DocumentCluster cluster) {
        if (this.cluster == cluster) {
            return false;
        } else {
            this.cluster = cluster;
            return true;
        }
    }
}
