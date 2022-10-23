package com.company.JavaFundamentals;

import java.math.BigInteger;
import java.util.Scanner;

public class Exercise5 {
    private static java.util.Scanner sc = new Scanner(System.in);

    public static void solveTask(){
        System.out.print("Input matrix length: ");
        int count = sc.nextInt();
        int[][] matr = new int[count][count];

        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                System.out.print("Input " + (i + 1) + " " + (j + 1) + " num of matrix: ");
                matr[i][j] = sc.nextInt();
            }
        }



    }
}
