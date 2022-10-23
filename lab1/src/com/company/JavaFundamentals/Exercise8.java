package com.company.JavaFundamentals;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Exercise8 {
    private static java.util.Scanner sc = new Scanner(System.in);

    private static int[] calcPositions(double[] arrA, double[] arrB) {
        double[] resArr = arrA.clone();
        int[] positions = new int[arrA.length];
        int index = 0;
        boolean flag = true;
        for (double insert : arrB) {
            for (int i = 0; i < resArr.length; i++) {
                if (resArr[i] >= insert && flag) {
                    positions[index] = i+index;
                    flag = false;
                }
            }
            index++;
            flag = true;
        }
        return positions;
    }


    public static void solveTask() {
        System.out.print("Input array length: ");
        int count = sc.nextInt();
        double[] arrA = new double[count];
        double[] arrB = new double[count];



        for (int i = 0; i < count; i++) {
            System.out.print("Input " + (i + 1) + " num of first array: ");
            arrA[i] = sc.nextDouble();
        }
        for (int i = 0; i < count; i++) {
            System.out.print("Input " + (i + 1) + " num of second array: ");
            arrB[i] = sc.nextDouble();
        }

        int[] positions = calcPositions(arrA, arrB);
        for(int i = 0; i < count; i++){
            System.out.print(positions[i] + " ");
        }

    }
}
