package com.company;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShopInfo implements IShopInfo{

    private String filepath;

    @Override
    public String getFullInfo() {
        List<String> infos = getStringsFromFile();
        return getInfoFromInfos(infos, "");
    }

    @Override
    public String getCategoryInfo(String category) {
        List<String> infos = getStringsFromFile();
        return getInfoFromInfos(infos, category);
    }

    @Override
    public String getLowCostProductInfo() {
        List<String> infos = getStringsFromFile();

        StringBuilder sb = new StringBuilder();
        StringBuilder tempSB = new StringBuilder();
        StringBuilder resSB = new StringBuilder();

        int index = 0, lowCostIndex = 1, lastIndex = 0;
        double lowCost = -1, tempCost = 0;
        String temp = "";


        for(int i = 1; i < infos.size(); i++){
            String[] infoArr = infos.get(i).split(" ");
            sb.append(infos.get(i));
            lastIndex = sb.lastIndexOf("<") - 1;

            tempSB.append(sb.substring(sb.indexOf("<", index)+1, sb.indexOf(">", index)) + " : ");
            index = sb.indexOf(">", index)+1;
            while(index < lastIndex){
                temp = sb.substring(sb.indexOf("<", index)+1, sb.indexOf(">", index));
                tempSB.append(temp + "=");
                index = sb.indexOf(">", index)+1;

                if (temp.equals("PRICE")){
                    tempCost = Double.parseDouble(sb.substring(index, sb.indexOf("<", index)));
                    if (tempCost < lowCost || lowCost == -1) {
                        lowCost = tempCost;
                        lowCostIndex = i;
                    }
                }

                tempSB.append(sb.substring(index, sb.indexOf("<", index)) + " ");
                index = sb.indexOf(">", index)+1;

            }

            if(lowCostIndex == i){
                resSB.delete(0, resSB.length());
                resSB.append(tempSB);
            }

            tempSB.delete(0, tempSB.length());
            sb.delete(0, sb.length());
            index = 0;
        }

        return resSB.toString();
    }

    private String getInfoFromInfos(List<String> infos, String category) {
        StringBuilder sb = new StringBuilder();
        StringBuilder resSB = new StringBuilder();
        for(int i = 1; i < infos.size(); i++){
            String[] infoArr = infos.get(i).split(" ");
            sb.append(infos.get(i));
            int index = 0;
            int lastIndex = sb.lastIndexOf("<") - 1;
            if(category.equals("") || category.equals(sb.substring(sb.indexOf("<", index)+1, sb.indexOf(">", index)))){

                resSB.append(sb.substring(sb.indexOf("<", index)+1, sb.indexOf(">", index)) + " : ");
                index = sb.indexOf(">", index)+1;
                while(index < lastIndex){
                    resSB.append(sb.substring(sb.indexOf("<", index)+1, sb.indexOf(">", index)) + "=");
                    index = sb.indexOf(">", index)+1;
                    resSB.append(sb.substring(index, sb.indexOf("<", index)) + " ");
                    index = sb.indexOf(">", index)+1;

                }
                resSB.append("\n");
            }
            sb.delete(0, sb.length());

        }
        return resSB.toString();
    }


    private List<String> getStringsFromFile() {
        List<String> appliancesInfos = new ArrayList<>();

        try {
            FileReader fr = new FileReader(filepath);
            Scanner scanner = new Scanner(fr);

            while (scanner.hasNext()) {
                appliancesInfos.add(scanner.nextLine());
            }

            fr.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return appliancesInfos;
        }
    }


    public ShopInfo(){

    }

    public ShopInfo(String filepath){
        this.filepath = filepath;
    }


}
