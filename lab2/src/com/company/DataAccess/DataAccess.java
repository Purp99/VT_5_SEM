package com.company.DataAccess;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;


public class DataAccess implements IDataAccess {
    private final String filepath;

    public DataAccess(String filepath) {
        this.filepath = filepath;
    }


    @Override
    public NodeList getNodeList() throws ParserConfigurationException, IOException, SAXException {
        File file = new File(filepath);
        if(file.exists()) {

            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();

            DocumentBuilder builder = factory.newDocumentBuilder();
            org.w3c.dom.Document doc = builder.parse(filepath);

            return doc.getElementsByTagName("*");
        }
        return null;
    }


}
