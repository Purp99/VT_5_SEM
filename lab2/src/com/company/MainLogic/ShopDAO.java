package com.company.MainLogic;


import com.company.DataAccess.IDataAccess;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShopDAO {

    private IDataAccess dataAccess;

    public ShopDAO(IDataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    public List<Product> getProductsDAO() throws IOException, ParserConfigurationException, SAXException {

        List<Product> products = new ArrayList<Product>();
        NodeList list = dataAccess.getNodeList();
        Product product = null;
        String parentName = list.item(0).getNodeName();



        for (int i = 0; i < list.getLength(); i++) {
            Element element = (Element) list.item(i);
            if(!element.getNodeName().equals(parentName)) {
                if (element.getParentNode().getNodeName().equals(parentName)) {
                    if (product != null) products.add(product);
                    product = new Product(element.getNodeName());
                } else {
                    assert product != null;
                    product.addProperty(element.getNodeName(), element.getFirstChild().getTextContent());
                }
            }
        }
        return products;
    }
}
