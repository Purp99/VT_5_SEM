package com.company.JavaFundamentals;

import java.util.Scanner;

public class Exercise3 {

    private static java.util.Scanner sc = new Scanner(System.in);

    public static void solveTask(){
        double a = sc.nextDouble();
        double b = sc.nextDouble();
        double h = sc.nextDouble();

        double temp = a;
        while (temp <= b) {
            System.out.println(temp +" "+ Math.tan(temp));
            temp += h;
        }


    }
}
