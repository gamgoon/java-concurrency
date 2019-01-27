package com.github.gamgoon.concurrency.ch11;

public class WordCounter {
    private final int counter;
    private final boolean lastSpace;

    public WordCounter(int counter, boolean lastSpace) {
        this.counter = counter;
        this.lastSpace = lastSpace;
    }

    public WordCounter accumulate(Character c) {
        if (Character.isWhitespace(c)) {
            System.out.printf("%s: %c accumulate %d\n", Thread.currentThread().getName(), c, counter);
            return lastSpace ? this : new WordCounter(counter, true);
        } else {
            System.out.printf("%s: %c accumulate %d\n", Thread.currentThread().getName(), c,
                    lastSpace ? counter + 1 : counter);
            return lastSpace ? new WordCounter(counter + 1, false) : this;
        }
    }

    public WordCounter combine(WordCounter wordCounter) {
        System.out.printf("%s: combine %d , %d\n", Thread.currentThread().getName(), this.counter, wordCounter.counter);
        return new WordCounter(counter + wordCounter.counter, wordCounter.lastSpace);
    }

    public int getCounter() {
        return counter;
    }
}
