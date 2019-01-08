package com.github.gamgoon.concurrency.ch07.first.concurrent;

import com.github.gamgoon.concurrency.ch07.first.Document;
import com.github.gamgoon.concurrency.ch07.first.DocumentCluster;
import com.github.gamgoon.concurrency.ch07.first.DocumentLoader;
import com.github.gamgoon.concurrency.ch07.first.VocabularyLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

public class ConcurrentMain {

    public static void main(String[] args) throws IOException {
        String path = "/Users/gamgoon/Downloads/9781785887949_Code/B05875_MasteringConcurrencyProgrammingWithJava9SecondEdition_Code/Chapter07/KMeansClustering/data";

        Path pathVoc = Paths.get(path,"movies.words");
        Map<String, Integer> vocIndex = VocabularyLoader.load(pathVoc);
        System.out.println("Voc Size: " + vocIndex.size());

        Path pathDocs = Paths.get(path,"movies.data");
        Document[] documents = DocumentLoader.load(pathDocs, vocIndex);
        System.out.println("Document Size: " + documents.length);

//        if (args.length != 3) {
//            System.err.println("Please specify K, SEED, MIN_SIZE");
//            return;
//        }
        int K = 20; // Integer.valueOf(args[0]);
        int SEED = 50; // Integer.valueOf(args[1]);
        int MAX_SIZE = 20; // Integer.valueOf(args[2]);


        Date start, end;
        start = new Date();
        DocumentCluster[] clusters = ConcurrentKMeans.calculate(documents, K, vocIndex.size(), SEED, MAX_SIZE);
        end = new Date();
        System.out.println("K: " + K + "; SEED: " + SEED + "; MAX_SIZE: " + MAX_SIZE);
        System.out.println("Execution Time: " + (end.getTime() - start.getTime()));

        System.out.println(
                Arrays.stream(clusters).map(DocumentCluster::getDocumentCount).sorted(Comparator.reverseOrder())
                        .map(Object::toString).collect(Collectors.joining(", ", "Cluster sizes: ", "")));

    }

}
