package com.github.gamgoon.concurrency.ch07.first;

public class Word implements Comparable<Word> {
    private int index;
    private double tfidf;

    public Word() {
    }

    @Override
    public int compareTo(Word o) {
        return Double.compare(o.tfidf, this.tfidf);
    }

    public int getIndex() {
        return index;
    }

    public double getTfidf() {
        return tfidf;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setTfidf(double tfidf) {
        this.tfidf = tfidf;
    }
}
