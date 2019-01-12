package com.github.gamgoon.concurrency.ch11;

import java.util.Spliterator;
import java.util.function.Consumer;

public class WordCounterSpliterator implements Spliterator<Character> {
    private final String string;
    private int currentCharPos = 0;
    private int index;
    private int childIndex;
    public WordCounterSpliterator(String string, int index) {
        this.string = string;
        this.index = index;
        this.childIndex = index == 0 ? 1 : index * 10;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
        System.out.println(index + " - tryAdvance");
        action.accept(string.charAt(currentCharPos++));
        return currentCharPos < string.length();
    }

    @Override
    public Spliterator<Character> trySplit() {
        System.out.println(index + " - trySplit");
        int currentSize = string.length() - currentCharPos;
        if (currentSize < 10) {
            System.out.println(index + " split null");
            return null;
        }
        for (int splitPos = currentSize / 2 + currentCharPos; splitPos < string.length(); splitPos++) {
            if (Character.isWhitespace(string.charAt(splitPos))) {
                int nextChildIndex = childIndex++;
                Spliterator<Character> spliterator = new WordCounterSpliterator(string.substring(currentCharPos, splitPos), nextChildIndex);
                currentCharPos = splitPos;
                System.out.println(index + " split " + nextChildIndex);
                return spliterator;
            }
        }
        System.out.println(index + " split null");
        return null;
    }

    @Override
    public long estimateSize() {
        System.out.println(index + " - estimateSize = " + (string.length() - currentCharPos));
        return string.length() - currentCharPos;
    }

    @Override
    public int characteristics() {
        System.out.println(index + " - characteristics = " + ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE);
        return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
    }
}
