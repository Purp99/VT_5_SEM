package com.company;


import com.company.MainLogic.ShopInfo;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main{

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

        ShopInfo shop = new ShopInfo("src\\com\\company\\Database\\shop.xml");

        System.out.println(shop.getFullInfo());
        System.out.println(shop.getCategoryInfo("FRIDGE"));
        System.out.println(shop.getLowCostProductInfo());
    }
}
