package com.company.JavaFundamentals;

import java.util.Scanner;

public class Exercise7 {

    private static java.util.Scanner sc = new Scanner(System.in);

    private static double[] sort(double[] array) {
        int n = array.length;
        for (int i = 0; i < n; i ++) {
            int minIndex = min(array, i, n - 1);
            swap(array, i, minIndex);
        }
        return array;
    }

    private static void swap(double[] array, int i, int j) {
        double temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    private static int min(double[] array, int begin, int end) {
        double minVal = array[begin];
        int minIndex = begin;
        for (int i = begin + 1; i <= end; i++) {
            if (array[i] < minVal) {
                minVal = array[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    public static void solveTask(){
        System.out.print("Input array length: ");
        int count = sc.nextInt();
        double[] arr = new double[count];
        double tmp;

        for (int i = 0; i < count; i++) {
            System.out.print("Input " + (i + 1) + " num of array: ");
            arr[i] = sc.nextDouble();
        }

        arr = sort(arr);

        for (int i = 0; i < count; i++) {
            System.out.print(arr[i] + " ");
        }
    }
}
