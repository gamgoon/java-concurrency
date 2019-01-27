package com.github.gamgoon.corejava;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Ex07 {
    public static void main(String[] args) {
        ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();
        map.put("a", 101L);
        map.put("b", 1030L);
        map.put("c", 10L);
        map.put("d", 110L);
        Map.Entry<String, Long> entry =
                map.reduceEntries(Long.MAX_VALUE, (b, n) -> b.getValue() > n.getValue() ? b : n);
        System.out.println(entry);
    }
}
