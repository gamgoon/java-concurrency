package com.github.gamgoon.concurrency.ch10.second;

import java.util.Set;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicLong;

public class MySubscription implements Flow.Subscription {
    private boolean cancelled = false;
    private AtomicLong requested = new AtomicLong(0);
    private Set<Integer> categories;

    @Override
    public void request(long n) {
        requested.addAndGet(n);
    }

    @Override
    public void cancel() {
        cancelled = true;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public long getRequested() {
        return requested.get();
    }

    public void decreaseRequested() {
        requested.decrementAndGet();
    }

    public void setCategories(Set<Integer> categories) {
        this.categories = categories;
    }

    public boolean hasCategory(Integer category) {
        return categories.contains(category);
    }
}
