package com.company.JavaFundamentals;

import java.util.Scanner;

public class Exercise1 {

    private static java.util.Scanner sc = new Scanner(System.in);

    public static double solveTask(){
        double res = 0;

        double x = sc.nextDouble();
        double y = sc.nextDouble();

        res = (1 + Math.pow(Math.sin(x + y), 2))/(2 + Math.abs(x - ((2*x)/(1+x*x*y*y)))) + x;

        return res;
    }
}
