package com.github.gamgoon.concurrency.ch10.second;

import java.util.Set;
import java.util.concurrent.Flow;

public class Consumer implements Flow.Subscriber<News> {
    private MySubscription subscription;
    private String name;
    private Set<Integer> categories;

    public Consumer(String name, Set<Integer> categories) {
        this.name = name;
        this.categories = categories;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = (MySubscription) subscription;
        this.subscription.setCategories(this.categories);
        this.subscription.request(1);
        System.out.printf("%s: Consumer - Subscription\n",
                Thread.currentThread().getName());
    }

    @Override
    public void onNext(News item) {
        System.out.printf("%s - %s: Consumer - News\n", name,
                Thread.currentThread().getName());
        System.out.printf("%s - %s: Text: %s\n", name,
                Thread.currentThread().getName(), item.getTxt());
        System.out.printf("%s - %s: Category: %s\n", name,
                Thread.currentThread().getName(),
                item.getCategory());
        System.out.printf("%s - %s: Date: %s\n", name,
                Thread.currentThread().getName(), item.getDate());
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.printf("%s - %s: Consumer - Error: %s\n", name,
                Thread.currentThread().getName(),
                throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.printf("%s - %s: Consumer - Completed\n", name,
                Thread.currentThread().getName());
    }
}
