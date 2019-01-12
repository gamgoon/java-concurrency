package com.github.gamgoon.concurrency.ch10.second;

import java.util.concurrent.Flow;

public class ConsumerData {
    private Flow.Subscriber<News> consumer;
    private MySubscription subscription;

    public Flow.Subscriber<News> getConsumer() {
        return consumer;
    }

    public void setConsumer(Flow.Subscriber<News> consumer) {
        this.consumer = consumer;
    }

    public MySubscription getSubscription() {
        return subscription;
    }

    public void setSubscription(MySubscription subscription) {
        this.subscription = subscription;
    }
}
