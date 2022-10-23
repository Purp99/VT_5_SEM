package com.company.JavaFundamentals;

import java.util.Scanner;

public class Exercise6 {

    private static java.util.Scanner sc = new Scanner(System.in);

    public static void solveTask(){
        System.out.print("Input array length: ");
        int count = sc.nextInt();
        double[] arr = new double[count];
        double[][] matr = new double[count][count];

        for (int i = 0; i < count; i++) {
                System.out.print("Input " + (i + 1) + " num of array: ");
                arr[i] = sc.nextDouble();
        }

        int temp = 0, index = 0;

        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                if (j+temp >= count)
                    index = (j + temp) % count;
                else
                    index = j + temp;
                matr[i][j] = arr[index];
            }
            temp++;
        }


        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                System.out.print(matr[i][j] + " ");
            }
            System.out.println();
        }
    }
}
