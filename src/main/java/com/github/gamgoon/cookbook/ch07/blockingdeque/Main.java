package com.github.gamgoon.cookbook.ch07.blockingdeque;

import java.util.Date;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        LinkedBlockingDeque<String> requestList = new LinkedBlockingDeque<>(3);
        Client client = new Client(requestList);
        Thread thread = new Thread(client);
        thread.start();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                String request = requestList.take();
                System.out.printf("Main: Removed: %s at %s. Size: %d\n",
                        request, new Date(), requestList.size());
            }
            TimeUnit.MILLISECONDS.sleep(300);
        }
        System.out.printf("Main: End of the program.\n");
    }
}
