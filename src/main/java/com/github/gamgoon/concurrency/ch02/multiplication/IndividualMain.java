package com.github.gamgoon.concurrency.ch02.multiplication;

import java.util.Date;

public class IndividualMain {
    public static void main(String[] args) {
        double matrix1[][] = MatrixGenerator.generate(1000, 1000);
        double matrix2[][] = MatrixGenerator.generate(1000, 1000);
        System.out.println("matrix1.length is " + matrix1.length);
        System.out.println("matrix2[0].length is " + matrix2[0].length);
        double resultSerial[][] = new double[matrix1.length][matrix2[0].length];

        Date start = new Date();
        ParallelIndividualMultiplier.multiply(matrix1, matrix2, resultSerial);
        Date end = new Date();
        System.out.printf("Serial: %d%n", end.getTime() - start.getTime());
    }
}
