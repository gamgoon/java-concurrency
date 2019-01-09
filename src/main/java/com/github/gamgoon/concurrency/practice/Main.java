package com.github.gamgoon.concurrency.practice;

import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<MyRequest> reqList = getMyRequest();
        System.out.println(reqList);
        if (reqList != null && !reqList.isEmpty()) {
            MyService.processMyRequest(reqList);
        }
    }

    private static List<MyRequest> getMyRequest() {
        MyRequest req = new MyRequest();
        req.setpSeq("1");
        req.setBadgeCount("1");
        req.setJoinType("A");
        req.setLandingType("T");
        req.setLandinKey("S");
        req.setMessage("테스트");
        req.setMessageIos("테스트");
        req.setOsType("A");
        req.setPushGbn("C");
        req.setPushType("T");
        return List.of(req);
    }
}
