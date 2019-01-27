package com.github.gamgoon.corejava;

import java.util.Arrays;

public class Ex02 {
    public static void main(String[] args) {
        checkTime(getRandomArray(10));
        checkTime(getRandomArray(100));
        checkTime(getRandomArray(1000));
        checkTime(getRandomArray(10000));
        checkTime(getRandomArray(100000));
        checkTime(getRandomArray(1000000));
        checkTime(getRandomArray(10000000));
    }

    public static void checkTime(String[] arr) {
        long start = System.currentTimeMillis();
        Arrays.sort(arr);
        long end1 = System.currentTimeMillis();
        Arrays.parallelSort(arr);
        long end2 = System.currentTimeMillis();
        System.out.printf("Array size is %d, Arrays.sort : %d, Arrays.parallelSort : %d\n"
                , arr.length, end1 - start, end2 - end1);
    }

    public static String[] getRandomArray(int count) {
        String[] result = new String[count];
        for (int i = 0; i < count; i++) {
            result[i] = getAlphaNumericString(3);
        }
        return result;
    }

    public static String getAlphaNumericString(int n) {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}
