package sample.DataAccess;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sample.Server.UserData;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;


public class DataAccess{
    private static final String FILE_CLIENTS_PATH = ".\\clients\\clients.xml";
    private static final String FILE_CLIENTS_NORMAL_VIEW_PATH = ".\\clients\\clientsNormalView.xml";
    private static final String STUDENT_FILES_FOLDER_PATH = ".\\studentFiles\\";
    public DataAccess() { }

    public static Node getNode(String filepath) throws ParserConfigurationException, IOException, SAXException {
        File file = new File(filepath);
        if(file.exists()) {

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            org.w3c.dom.Document doc = builder.parse(filepath);


            return doc.getDocumentElement();
        }
        return null;
    }

    public static void writeClientData(UserData userData) throws IOException, ParserConfigurationException, SAXException {
        DocumentBuilder builder =
                DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(FILE_CLIENTS_PATH);

        Node root = doc.getDocumentElement();

        Element client = doc.createElement("client");

        Element id = doc.createElement("id");
        id.setTextContent(userData.getId());

        Element login = doc.createElement("login");
        login.setTextContent(userData.getLogin());

        Element password = doc.createElement("password");
        password.setTextContent(userData.getPassword());

        Element files = doc.createElement("files");

        client.appendChild(id);
        client.appendChild(login);
        client.appendChild(password);
        client.appendChild(files);
        root.appendChild(client);

        writeDocument(doc, FILE_CLIENTS_PATH);

        writeDocumentWithProperty(doc, FILE_CLIENTS_NORMAL_VIEW_PATH);
    }

    private static void writeDocument(Document document, String filepath) throws TransformerFactoryConfigurationError {
        try {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
            FileOutputStream fos = new FileOutputStream(filepath);
            StreamResult result = new StreamResult(fos);
            tr.transform(source, result);
            fos.close();
        } catch (TransformerException | IOException e) {
            e.printStackTrace(System.out);
        }

    }

    private static void writeDocumentWithProperty(Document document, String filepath) throws TransformerFactoryConfigurationError {
        try {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            FileOutputStream fos = new FileOutputStream(filepath);
            StreamResult result = new StreamResult(fos);
            tr.transform(source, result);
            fos.close();
        } catch (TransformerException | IOException e) {
            e.printStackTrace(System.out);
        }

    }

    public static boolean checkAuthentication(UserData userData) throws IOException, SAXException, ParserConfigurationException {
        Node root = getNode(FILE_CLIENTS_PATH);
        NodeList clientNodes = root.getChildNodes();
        if(clientNodes.getLength() < 1)
            return false;

        boolean isMatch = false;
        int i = 0;
        do{
            NodeList clientDataNode = clientNodes.item(i).getChildNodes();

            Node loginNode = clientDataNode.item(1).getFirstChild();

            if(loginNode.getNodeValue().equals(userData.getLogin())){
                Node passwordNode = clientDataNode.item(2).getFirstChild();
                if(passwordNode.getNodeValue().equals(userData.getPassword())){
                    isMatch = true;
                    NodeList filesNode = clientDataNode.item(3).getChildNodes();
                    for (int j = 0; j<filesNode.getLength();j++){
                        userData.addFile(filesNode.item(j).getFirstChild().getNodeValue());
                    }
                    userData.setId(clientDataNode.item(0).getFirstChild().getNodeValue());
                }
            }
            i++;
        }while(i < clientNodes.getLength() && !isMatch);

        return isMatch;
    }

    public static boolean checkLogin(String login) throws IOException, SAXException, ParserConfigurationException{
        Node root = getNode(FILE_CLIENTS_PATH);
        NodeList clientNodes = root != null ? root.getChildNodes() : null;
        if(clientNodes.getLength() < 1) {
            return true;
        }
        boolean isMatch = false;
        int i = 0;
        do{
            NodeList clientDataNode = clientNodes.item(i).getChildNodes();

            Node loginNode = clientDataNode.item(1).getFirstChild();

            if(loginNode.getNodeValue().equals(login)){
                isMatch = true;
            }
            i++;
        }while(i < clientNodes.getLength() && !isMatch);

        return !isMatch;
    }

    public static String getStudentFiles(){
        StringBuilder sb = new StringBuilder();
        File studentFilesDir = new File(STUDENT_FILES_FOLDER_PATH);
        File[] files = studentFilesDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".xml");
            }
        });
        if (files != null) {
            for(File f : files){
                sb.append(f.getName());
                sb.append("&");
            }
        }
        if(!sb.toString().equals("")) {
            sb.delete(sb.length()-1, sb.length());
        }

        return sb.toString();
    }

    public static boolean deleteStudentFile(String filename){
        File file = new File(STUDENT_FILES_FOLDER_PATH + filename);
        if(file.exists())
            return file.delete();
        return false;
    }

    public static boolean addStudentFile(UserData signupUser, String filename) throws IOException, SAXException, ParserConfigurationException {

        File file = new File(STUDENT_FILES_FOLDER_PATH+filename);
        if(file.exists())
            return false;
        else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        updateClientFiles(signupUser, filename);
        return true;
    }

    public static void setIDCounter() throws IOException, ParserConfigurationException, SAXException {
        Node root = getNode(FILE_CLIENTS_PATH);
        NodeList clientNodes = root.getChildNodes();
        if(clientNodes.getLength() != 0){
            NodeList clientDataNode = clientNodes.item(clientNodes.getLength()-1).getChildNodes();
            Node idNode = clientDataNode.item(0).getFirstChild();
            UserData.setIdCounter(Integer.parseInt(idNode.getNodeValue())+1);
        }else {
            UserData.setIdCounter(0);
        }




    }
    private static void updateClientFiles(UserData userData, String filename) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder =
                DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(FILE_CLIENTS_PATH);

        Node root = doc.getDocumentElement();
        NodeList clientNodes = root.getChildNodes();
        boolean isMatch = false;
        int i = 0;
        do{
            NodeList clientDataNode = clientNodes.item(i).getChildNodes();

            Node loginNode = clientDataNode.item(1).getFirstChild();

            if(loginNode.getNodeValue().equals(userData.getLogin())){
                isMatch = true;
            }
            i++;
        }while(i < clientNodes.getLength() && !isMatch);


        Element file = doc.createElement("file");
        file.setTextContent(filename);
        clientNodes.item(--i).getChildNodes().item(3).appendChild(file);


        writeDocument(doc, FILE_CLIENTS_PATH);

        writeDocumentWithProperty(doc, FILE_CLIENTS_NORMAL_VIEW_PATH);
    }

    public static void deleteClientFile(UserData userData, String filename) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder =
                DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(FILE_CLIENTS_PATH);

        Node root = doc.getDocumentElement();
        NodeList clientNodes = root.getChildNodes();
        boolean isMatch = false;
        int i = 0;
        do {
            NodeList clientDataNode = clientNodes.item(i).getChildNodes();

            Node loginNode = clientDataNode.item(1).getFirstChild();

            if(loginNode.getNodeValue().equals(userData.getLogin())){
                isMatch = true;
            }
            i++;
        } while(i < clientNodes.getLength() && !isMatch);



        NodeList fileNodes = clientNodes.item(--i).getChildNodes().item(3).getChildNodes();
        for (int j = 0; j<fileNodes.getLength();j++){
            String name = fileNodes.item(j).getFirstChild().getNodeValue();
            if (name.equals(filename)){
                clientNodes.item(i).getChildNodes().item(3).removeChild(fileNodes.item(j));
            }
        }

        writeDocument(doc, FILE_CLIENTS_PATH);

        writeDocumentWithProperty(doc, FILE_CLIENTS_NORMAL_VIEW_PATH);

    }

    public static String getStudentFileContent(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(STUDENT_FILES_FOLDER_PATH + filename));
        StringBuilder sb = new StringBuilder();
        while (br.ready()){
            sb.append(br.readLine());
        }
        br.close();
        return sb.toString();
    }

    public static void changeStudentFile(String filename, String content) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new FileOutputStream(STUDENT_FILES_FOLDER_PATH + filename));
        pw.println(content);
        pw.close();
    }

}
