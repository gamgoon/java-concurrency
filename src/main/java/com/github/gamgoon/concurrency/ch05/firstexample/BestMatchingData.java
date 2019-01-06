package com.github.gamgoon.concurrency.ch05.firstexample;

import java.util.List;

public class BestMatchingData {
    private List<String> words;
    private int distance;

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
