package com.github.gamgoon.concurrency.practice;

import java.util.List;

public class TypeBTask implements Runnable {
    private MyRequest request;

    public TypeBTask(MyRequest request) {
        this.request = request;
    }

    @Override
    public void run() {
        int index = 0;
        String lastKey = null;
        while (true) {
            List<Target> targets = MyDao.findTypeBTargets(request, index, lastKey);
            if (targets == null || targets.isEmpty()) {
                break;
            }
            // TODO: 별렬처리 포인트
            try {
                MyService.sendTypeB(request, targets);
            } catch (InterruptedException e) {
                continue;
            }
            index++;
            lastKey = targets.get(targets.size() - 1).getPushKey();
        }
    }
}
