package com.github.gamgoon.concurrency.practice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyService {
    public static void processMyRequest(List<MyRequest> reqList) throws InterruptedException {
        changeStatusAsProcessing(reqList);
        for (MyRequest req : reqList) {
            sendVersion2(req);
        }
    }

    private static void sendVersion2(MyRequest req) {
        printMessageWithTime("start");
        TypeBTask taskB = new TypeBTask(req);
        Thread threadB = new Thread(taskB);
        threadB.start();

        printMessageWithTime("end");
    }

    private static void sendVersion1(MyRequest req) throws InterruptedException {
        printMessageWithTime("start");

        List<Target> targetA = new ArrayList<>();
        List<Target> targetB = new ArrayList<>();
        List<Target> targetC = new ArrayList<>();
        int success = 0;
        int fail = 0;
        int index = 0;
        String lastKey = null;
        while (true) {
            List<Target> targetList = MyDao.findTargets(req, index, lastKey);
            if (targetList.isEmpty()) {
                break;
            }
            for (Target t : targetList) {
                if ("A".equals(t.getDeviceGbn())) {
                    targetA.add(t);
                } else if ("B".equals(t.getDeviceGbn())) {
                    targetB.add(t);
                } else {
                    targetC.add(t);
                }
            }
            if (!targetA.isEmpty()) {
                Result result = sendTypeA(req, targetA);
                success += result.getSuccess();
                fail += result.getFail();
                // TODO: result.message() 는 어디에 쓰지?
            }
            if (!targetB.isEmpty()) {
                Result result = sendTypeB(req, targetA);
                success += result.getSuccess();
                fail += result.getFail();
                // TODO: result.message() 는 어디에 쓰지?
            }
            if (!targetC.isEmpty()) {
                Result result = sendTypeC(req, targetA);
                success += result.getSuccess();
                fail += result.getFail();
                // TODO: result.message() 는 어디에 쓰지?
            }
            index++;
            lastKey = targetList.get(targetList.size() - 1).getPushKey();
            targetA.clear();
            targetB.clear();
            targetC.clear();
        }
        changeStatusAsComplete(req.getpSeq());
        printMessageWithTime("end");
    }

    public static void changeStatusAsComplete(String seq) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(300);
    }

    public static Result sendTypeA(MyRequest req, List<Target> targetA) throws InterruptedException {
        for (Target t : targetA) {
            TimeUnit.MILLISECONDS.sleep(300);
        }
        return new Result(1, 1, "");
    }

    public static Result sendTypeB(MyRequest req, List<Target> targetB) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(300);
        return new Result(1, 1, "");
    }

    public static Result sendTypeC(MyRequest req, List<Target> targetC) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(300);
        for (Target t : targetC) {
            TimeUnit.MILLISECONDS.sleep(300);
        }
        return new Result(1, 1, "");
    }

    private static void changeStatusAsProcessing(List<MyRequest> reqList) throws InterruptedException {
        for (MyRequest req : reqList) {
            TimeUnit.MILLISECONDS.sleep(300);
        }
    }

    public static void printMessageWithTime(String msg) {
        System.out.println(msg + " : " + System.currentTimeMillis() + " " + LocalDateTime.now());
    }
}
