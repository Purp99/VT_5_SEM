package com.company.JavaFundamentals;

import java.util.Scanner;

public class Exercise2 {

    private static java.util.Scanner sc = new Scanner(System.in);

    public static boolean solveTask(){

        double x = sc.nextDouble();
        double y = sc.nextDouble();

        if (x >= -6 && x <= 6 && y >= -3 && y <= 0)
            return true;
        if (x >= -4 && x <= 4 && y >= 0 && y <= 5)
            return true;
        return true;
    }
}
