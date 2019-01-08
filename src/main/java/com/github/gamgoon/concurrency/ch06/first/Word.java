package com.github.gamgoon.concurrency.ch06.first;

public class Word implements Comparable<Word> {
    private String word;
    private int tf;
    private int df;
    private double tfIdf;

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", tf=" + tf +
                ", df=" + df +
                ", tfIdf=" + tfIdf +
                '}';
    }

    public Word(String word) {
        this.word = word;
        this.tf = 1;
    }

    public void addTf() {
        this.tf++;
    }

    public int getTf() {
        return tf;
    }

    public int getDf() {
        return df;
    }

    public double getTfIdf() {
        return tfIdf;
    }

    public String getWord() {
        return word;
    }

    public Word merge(Word other) {
        if (this.word.equals(other.word)) {
            this.tf += other.tf;
            this.df += other.df;
        }
        return this;
    }


    public void setDf(int df, int N) {
        this.df = df;
        this.tfIdf = tf * Math.log(Double.valueOf(N) / df);
    }

    @Override
    public int compareTo(Word o) {
        return Double.compare(o.getTfIdf(), this.getTfIdf());
    }
}

