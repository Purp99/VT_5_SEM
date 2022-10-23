package com.company;

public class Main{

    public static void main(String[] args) {

        ShopInfo shop = new ShopInfo("src\\com\\company\\shop.xml");
        System.out.println(shop.getFullInfo());
        System.out.println(shop.getCategoryInfo("FRIDGE"));
        System.out.println(shop.getLowCostProductInfo());
    }
}
