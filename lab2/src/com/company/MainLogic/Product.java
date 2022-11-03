package com.company.MainLogic;

import java.util.HashMap;


public class Product {
    private HashMap<String, String> properties;
    private final String name;

    public Product(String name){
        this.name = name;
        properties = new HashMap<String, String>();
    }

    public void addProperty(String propertyName, String propertyValue){
        properties.put(propertyName, propertyValue);
    }

    public String getName(){
        return name;
    }

    public int getPropertiesSize(){
        return properties.size();
    }
    public String getPropertyValue(int i){
        return properties.values().toArray()[i].toString();
    }
    public String getPropertyKey(int i){
        return properties.keySet().toArray()[i].toString();
    }
    public String  getPropertyValue(String key){
        return properties.get(key);
    }
}
