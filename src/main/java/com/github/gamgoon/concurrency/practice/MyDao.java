package com.github.gamgoon.concurrency.practice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyDao {
    public static List<Target> findTypeBTargets(MyRequest req, int index, String lastPushKey) {
        if (index + 1 == 10) {
            return Collections.emptyList();
        }
        List<Target> retList = new ArrayList<>();
        for (int i = 0; i <= 300; i++) {
            Target t = new Target();
            t.setPushKey(String.valueOf(i));
            t.setAppVersion("B-1");
            t.setDeviceGbn("B");
            t.setDeviceId(String.valueOf(i));
            retList.add(t);
        }
        return retList;
    }
    public static List<Target> findTargets(MyRequest req, int index, String lastPushKey) {
        if (index + 1 == 10) {
            return Collections.emptyList();
        }
        List<Target> retList = new ArrayList<>();
        for (int i = 0; i <= 100; i++) {
            Target t = new Target();
            t.setPushKey(String.valueOf(i));
            t.setAppVersion("A-1");
            t.setDeviceGbn("A");
            t.setDeviceId(String.valueOf(i));
            retList.add(t);
        }
        for (int i = 0; i <= 300; i++) {
            Target t = new Target();
            t.setPushKey(String.valueOf(i));
            t.setAppVersion("B-1");
            t.setDeviceGbn("B");
            t.setDeviceId(String.valueOf(i));
            retList.add(t);
        }
        for (int i = 0; i <= 500; i++) {
            Target t = new Target();
            t.setPushKey(String.valueOf(i));
            t.setAppVersion("B-2");
            t.setDeviceGbn("C");
            t.setDeviceId(String.valueOf(i));
            retList.add(t);
        }
        return retList;
    }
}
