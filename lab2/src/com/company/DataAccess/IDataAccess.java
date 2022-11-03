package com.company.DataAccess;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface IDataAccess {

    NodeList getNodeList() throws ParserConfigurationException, IOException, SAXException;
}
