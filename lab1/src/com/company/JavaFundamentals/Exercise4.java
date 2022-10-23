package com.company.JavaFundamentals;

import java.math.BigInteger;
import java.util.Scanner;

public class Exercise4 {

    private static java.util.Scanner sc = new Scanner(System.in);

    public static void solveTask(){
        System.out.print("Input array length: ");
        int count = sc.nextInt();
        BigInteger bigInteger;
        int[] arr = new int[count];
        for (int i = 0; i < arr.length; i++) {
            System.out.print("Input " + (i+1) + " num of array: ");
            arr[i] = sc.nextInt();
        }

        for (int i = 0; i < arr.length; i++) {

            bigInteger = BigInteger.valueOf(arr[i]);
            if (bigInteger.isProbablePrime((int) Math.log(arr[i])))
                System.out.print(i +" ");

        }
    }
}
