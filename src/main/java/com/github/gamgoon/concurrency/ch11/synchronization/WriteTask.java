package com.github.gamgoon.concurrency.ch11.synchronization;

import java.util.List;
import java.util.function.Consumer;

public class WriteTask implements Consumer<List<Product>> {
    @Override
    public void accept(List<Product> productList) {
        // TODO:
        System.out.println("generate HTML");
    }
}
