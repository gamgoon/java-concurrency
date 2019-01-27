package com.github.gamgoon.corejava;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class Ex04 {
    public static void main(String[] args) {
        int n = 15;
        Matrix[] matArr = new Matrix[n];
        IntStream.range(0, n).forEach(i -> matArr[i] = new Matrix());
        Arrays.parallelPrefix(matArr, Matrix::multiply);
        OptionalInt result = Arrays.stream(matArr).peek(System.out::println)
                .mapToInt(Matrix::result)
                .reduce((first, second) -> second);
        System.out.println("result is " + result);
    }

    public static class Matrix {
        private int[][] value = {{1, 1}, {1, 0}};

        public Matrix() {
        }

        public Matrix(int[][] value) {
            this.value = value;
        }

        public Matrix multiply(Matrix matrix) {
            int[][] result = {{value[0][0] * matrix.value[0][0] + value[0][1] * matrix.value[1][0],
                    value[0][0] * matrix.value[1][0] + value[0][1] * matrix.value[1][1]},
                    {value[1][0] * matrix.value[0][0] + value[1][1] * matrix.value[1][0],
                            value[1][0] * matrix.value[1][0] + value[1][1] * matrix.value[1][1]}};
            return new Matrix(result);
        }

        public int result() {
            return value[0][1];
        }

        @Override
        public String toString() {
            return "[[" + value[0][0] + "," + value[0][1] + "]," + "[" + value[1][0] + "," + value[1][1] + "]]";
        }
    }
}
