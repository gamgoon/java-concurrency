package com.github.gamgoon.concurrency.ch05.secondexample.serial;

import com.github.gamgoon.concurrency.ch05.secondexample.DocumentParser;

import java.io.File;
import java.util.*;

public class SerialIndexing {
    public static void main(String[] args) {
        String filePath = "/Users/gamgoon/Downloads/9781785887949_Code/B05875_MasteringConcurrencyProgrammingWithJava9SecondEdition_Code/Chapter05/TextIndexing/data";
        Date start, end;
        File source = new File(filePath);
        File[] files = source.listFiles();
        Map<String, List<String>> invertedIndex = new HashMap<>();
        start = new Date();
        for (File file : files) {
            DocumentParser parser = new DocumentParser();
            if (file.getName().endsWith(".txt")) {
                Map<String, Integer> voc = parser.parse(file.getAbsolutePath());
                updateInvertedIndex(voc, invertedIndex, file.getName());
            }
        }
        end = new Date();
        System.out.println("Execution Time: " + (end.getTime() - start.getTime()));
        System.out.println("invertedIndex: " + invertedIndex.size());
    }

    private static void updateInvertedIndex(Map<String, Integer> voc,
                                            Map<String, List<String>> invertedIndex, String filename) {
        for (String word : voc.keySet()) {
            if (word.length() >= 3) {
                invertedIndex.computeIfAbsent(word, k -> new ArrayList<>()).add(filename);
            }
        }
    }
}
