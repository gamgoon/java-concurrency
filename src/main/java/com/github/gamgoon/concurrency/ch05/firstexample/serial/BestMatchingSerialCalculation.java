package com.github.gamgoon.concurrency.ch05.firstexample.serial;

import com.github.gamgoon.concurrency.ch05.firstexample.BestMatchingData;
import com.github.gamgoon.concurrency.ch05.firstexample.LevenshteinDistance;

import java.util.ArrayList;
import java.util.List;

public class BestMatchingSerialCalculation {
    public static BestMatchingData getBestMatchingWords(String word, List<String> dictionary) {
        List<String> results = new ArrayList<>();
        int minDistance = Integer.MAX_VALUE;
        int distance;

        for (String str : dictionary) {
            distance = LevenshteinDistance.calculate(word, str);
            if (distance < minDistance) {
                results.clear();
                minDistance = distance;
                results.add(str);
            } else if (distance == minDistance) {
                results.add(str);
            }
        }
        BestMatchingData result = new BestMatchingData();
        result.setWords(results);
        result.setDistance(minDistance);
        return result;
    }
}
