package com.github.gamgoon.concurrency.ch11.synchronization;

import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SearchTask implements Function<List<Product>, List<Product>> {
    private String query;

    public SearchTask(String query) {
        this.query = query;
    }

    @Override
    public List<Product> apply(List<Product> productList) {
        System.out.println(new Date() + ": CompletableTask: start");
        List<Product> ret = productList.stream()
                .filter(product -> product.getTitle().toLowerCase().contains(query))
                .collect(Collectors.toList());
        System.out.println(new Date() + ": CompletableTask: end: " + ret.size());
        return ret;
    }
}
