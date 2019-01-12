package com.github.gamgoon.concurrency.ch10.first;

import java.util.Random;
import java.util.concurrent.Flow;
import java.util.concurrent.TimeUnit;

public class Consumer implements Flow.Subscriber<Event> {
    private String name;
    private Flow.Subscription subscription;

    public Consumer(String name) {
        this.name = name;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
//        this.subscription.request(Integer.MAX_VALUE);
        this.subscription.request(2);
        this.showMessage("Subscription OK");
    }

    @Override
    public void onNext(Event event) {
        this.showMessage("An event has arrived: " + event.getSource() +
                ":" + event.getDate() + ":" + event.getMsg());
        this.subscription.request(1);
        processEvent(event);
    }

    @Override
    public void onError(Throwable throwable) {
        this.showMessage("An error has ocurred");
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        showMessage("No more events");
    }

    private void showMessage(String txt) {
        System.out.println(Thread.currentThread().getName() + ":" + this.name + ":" + txt);
    }

    private void processEvent(Event event) {
        Random random = new Random();
        int number = random.nextInt(3);
        try {
            TimeUnit.SECONDS.sleep(number);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
