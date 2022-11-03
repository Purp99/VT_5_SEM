package com.company.MainLogic;

import com.company.DataAccess.DataAccess;
import com.company.DataAccess.IDataAccess;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;


public class ShopInfo implements IShopInfo {

    private final String filepath;
    private IDataAccess dataAccess;
    private ShopDAO dao;
    private List<Product> products;

    @Override
    public String getFullInfo() {
        StringBuilder sb = new StringBuilder();
        for (Product p : products){
            sb.append(p.getName()).append(" : ");
            for (int i = 0; i < p.getPropertiesSize(); i++){
                sb.append(p.getPropertyKey(i)).append("=").append(p.getPropertyValue(i)).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public String getCategoryInfo(String category) {
        StringBuilder sb = new StringBuilder();
        for (Product p : products){
            if(p.getName().equals(category)){
                sb.append(p.getName()).append(" : ");
                for (int i = 0; i < p.getPropertiesSize(); i++){
                    sb.append(p.getPropertyKey(i)).append("=").append(p.getPropertyValue(i)).append(" ");
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public String getLowCostProductInfo() {
        StringBuilder sb = new StringBuilder();
        int lowcost = -1, index = -1;

        for (int j = 0; j < products.size(); j++){
           if(Integer.parseInt(products.get(j).getPropertyValue("PRICE")) < lowcost || lowcost == -1){
                lowcost = Integer.parseInt(products.get(j).getPropertyValue("PRICE"));
                index = j;
           }
        }

        sb.append(products.get(index).getName()).append(" : ");
        for (int i = 0; i < products.get(index).getPropertiesSize(); i++){
            sb.append(products.get(index).getPropertyKey(i)).append("=").append(products.get(index).getPropertyValue(i)).append(" ");
        }

        return sb.toString();
    }

    public ShopInfo(String filepath) throws ParserConfigurationException, SAXException, IOException {
        this.filepath = filepath;
        dataAccess = new DataAccess(filepath);
        dao = new ShopDAO(dataAccess);
        products = dao.getProductsDAO();
    }


}
