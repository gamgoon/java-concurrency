package com.github.gamgoon.concurrency.ch05.firstexample.concurrent;

import com.github.gamgoon.concurrency.ch05.firstexample.BestMatchingData;
import com.github.gamgoon.concurrency.ch05.firstexample.WordsLoader;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BestMatchingAdvancedConcurrentMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Date startTime, endTime;
        List<String> dictionary = WordsLoader.load("data/UK Advanced Cryptics Dictionary.txt");
        System.out.println("Dictionary Size: " + dictionary.size());
        String keyword = "gamgoon";
        startTime = new Date();
        BestMatchingData result = BestMatchingAdvancedConcurrentCalculation.getBestMatchingWords(keyword, dictionary);
        List<String> results = result.getWords();
        endTime = new Date();
        System.out.println("Word: " + keyword);
        System.out.println("Minimum distance: " + result.getDistance());
        System.out.println("List of best matching words: " + results.size());
        results.forEach(System.out::println);
        System.out.println("Execution time: " + (endTime.getTime() - startTime.getTime()));
    }
}
