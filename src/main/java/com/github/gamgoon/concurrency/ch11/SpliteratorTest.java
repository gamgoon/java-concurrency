package com.github.gamgoon.concurrency.ch11;

import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class SpliteratorTest {
    public static int countWordsIteratively(String s) {
        int counter = 0;
        boolean lastSpace = true;
        for (char c : s.toCharArray()) {
            if (Character.isWhitespace(c)) {
                lastSpace = true;
            } else {
                if (lastSpace) {
                    counter ++;
                }
                lastSpace = false;
            }
        }
        return counter;
    }

    public static void main(String[] args) {
        final String SENTENCE = " Nel mezzo del cammin di nostra vita " +
                "mi ritroval in una selva oscura" +
                " ch la dritta via era smarrita ";

//        System.out.println("Found " + countWordsIteratively(SENTENCE) + " words");
//
//        Stream<Character> stream = IntStream.range(0, SENTENCE.length())
//                .mapToObj(SENTENCE::charAt);
//        System.out.println("Found " + countWord(stream) + " words");

//        Stream<Character> stream2 = IntStream.range(0, SENTENCE.length())
//                .mapToObj(SENTENCE::charAt);
//        System.out.println("Found " + countWord(stream2.parallel()) + " words");
//
        Spliterator<Character> spliterator = new WordCounterSpliterator(SENTENCE, 0);
        Stream<Character> stream3 = StreamSupport.stream(spliterator, true);
        System.out.println("Found " + countWord(stream3) + " words");
    }

    private static int countWord(Stream<Character> stream) {
        WordCounter wordCounter = stream.reduce(new WordCounter(0, true),
                WordCounter::accumulate,
                WordCounter::combine);
        return wordCounter.getCounter();
    }
}
