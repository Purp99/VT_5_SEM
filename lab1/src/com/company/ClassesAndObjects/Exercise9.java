package com.company.ClassesAndObjects;

import java.util.*;

public class Exercise9 {

    public class Ball{
        private double weight;
        private String color;

        public Ball(double weight, String color) {
            this.weight = weight;
            this.color = color;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

    public class Busket{
        private ArrayList<Ball> balls;

        public Busket(ArrayList<Ball> balls) {
            this.balls = balls;
        }

        public void addBall(Ball ball){
            balls.add(ball);
            System.out.println("Added " + ball.color + " ball (" + ball.weight + " weight)");
        }

        public double getWeight(){
            double res = 0;
            for (Ball b : balls) {
                res += b.getWeight();
            }
            return res;
        }

        public int getBallsCount(String color){
            int res = 0;
            for (Ball b : balls) {
                if(b.color.equals(color))
                    res++;
            }
            return res;
        }
    }


    public void solveTask(){
        Busket busket = new Busket(new ArrayList<Ball>());

        busket.addBall(new Ball(2.5,"red"));
        busket.addBall(new Ball(1,"green"));
        busket.addBall(new Ball(3,"red"));
        busket.addBall(new Ball(2,"blue"));
        busket.addBall(new Ball(1.5,"blue"));

        System.out.println("Red balls count = " + busket.getBallsCount("red"));
        System.out.println("Weight = " + busket.getWeight());
    }

}
