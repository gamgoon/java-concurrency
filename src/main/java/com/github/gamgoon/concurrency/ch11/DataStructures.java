package com.github.gamgoon.concurrency.ch11;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class DataStructures {
    public static void main(String[] args) {
        String dirpath = "/Users/gamgoon/Downloads/9781785887949_Code/B05875_MasteringConcurrencyProgrammingWithJava9SecondEdition_Code/Chapter11/DataStructures/data/amazon";

        Path path = Paths.get(dirpath, "1995.txt");
        ConcurrentHashMap<BasciProduct, ConcurrentLinkedDeque<BasicReview>> products1995 =
                BasicProductLoader.load(path);
        showData(products1995);

        path = Paths.get(dirpath, "1995.txt");
        ConcurrentHashMap<BasciProduct, ConcurrentLinkedDeque<BasicReview>> products1996 =
                BasicProductLoader.load(path);
        showData(products1996);

        // merge
        products1996.forEach(10, (product, reviews) -> {
            products1995.merge(product, reviews, (review1, review2) -> {
                System.out.println("Merge for : " + product.getAsin());
                review1.addAll(review2);
                return review1;
            });
        });

        showData(products1995);
    }

    private static void showData(ConcurrentHashMap<BasciProduct, ConcurrentLinkedDeque<BasicReview>> products) {
        System.out.println(products.size());
        products.forEach((product, reviews) -> System.out.println(product + " : " + reviews.size()));
        System.out.println();

    }
}
