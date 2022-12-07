package sample.Server;

import org.xml.sax.SAXException;
import sample.Client;
import sample.DataAccess.DataAccess;
import sample.Methods;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.ServerSocket;
import java.nio.file.*;
import java.util.*;

public class Server {

    private static List<Client> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        if(createFiles()) {
            DataAccess.setIDCounter();
            ServerSocket serverSocket = new ServerSocket(8888);
            boolean flag = false;
            while (!flag) {
                clients.add(new Client(serverSocket.accept()));
                new Thread(new ClientHandler(clients.get(clients.size() - 1))).start();
                flag = true;
            }
        }
    }

    private static boolean createFiles(){
        try {
            Path path = Paths.get("./clients/clients.xml");
            if (!Files.exists(path)){
                new File("./clients").mkdirs();
                Files.createFile(path);
                path = Paths.get("./clients/clientsNormalView.xml");
                Files.createFile(path);
                PrintWriter pw = new PrintWriter(new FileOutputStream(".\\clients\\clients.xml"));
                pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><clients></clients>");
                pw.close();
            }

            path = Paths.get("./studentFiles");
            if (!Files.exists(path)){
                new File("./studentFiles").mkdirs();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

class ClientHandler implements Runnable {

    private final Client client;
    private static UserData signUser = null;

    public ClientHandler(Client client) {
        this.client = client;
    }

    public void run()
    {
        try {
            StringBuilder request = new StringBuilder();
            StringBuilder requestWithoutParams = new StringBuilder();
            while (!request.toString().equals("Exit")) {
                if(!request.append(client.getLineFromServer()).toString().equals("")) {
                    if (request.indexOf("?") != -1)
                        requestWithoutParams.append(request.substring(0, request.indexOf("?")));
                    switch (requestWithoutParams.toString()) {
                        case "Signin" -> doSignin(request.substring(request.indexOf("?") + 1));
                        case "Signup" -> doSignup(request.substring(request.indexOf("?") + 1));
                        case "CheckLogin" -> checkLogin(request.substring(request.indexOf("?") + 1));
                        case "GetAllStudentFiles" -> getAllStudentFiles();
                        case "DeleteStudentFile" -> deleteStudentFile(request.substring(request.indexOf("?") + 1));
                        case "AddStudentFile" -> addStudentFile(request.substring(request.indexOf("?") + 1));
                        case "ShowStudentFile" -> showStudentFile(request.substring(request.indexOf("?") + 1));
                        case "ChangeStudentFile" -> changeStudentFile(request.substring(request.indexOf("?") + 1));
                        case "CheckPermission" -> checkPermission(request.substring(request.indexOf("?")+1));
                    }
                    request.delete(0, request.length());
                    requestWithoutParams.delete(0, requestWithoutParams.length());
                }
            }
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void doSignup(String params){

        try {
            UserData userData = new UserData(Methods.getParam(params,"Login"), Methods.getParam(params, "Password"));
            UserData.updateID();
            DataAccess.writeClientData(userData);
            client.printlnToServer("Signup accept");
        }catch (FileNotFoundException | NoSuchFileException e) {
            client.printlnToServer("Signup error");
            System.out.println("file not found");
        } catch(IOException e) {
            client.printlnToServer("Signup error");
            System.out.println("IO exceptions");
        } catch (SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void doSignin(String params){
        try {

            UserData userData = new UserData(Methods.getParam(params,"Login"), Methods.getParam(params, "Password"));
            if(DataAccess.checkAuthentication(userData)){
                client.printlnToServer("Authentication accept");
                signUser = userData;
            } else {
                client.printlnToServer("Authentication error");
                signUser = null;
            }
        } catch (IOException e) {
            client.printlnToServer("Authentication error");
            signUser = null;
        } catch (SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void checkLogin(String params){
        try {
            if(DataAccess.checkLogin(Methods.getParam(params, "Login"))){
                client.printlnToServer("Check_Login accept");
            } else {
                client.printlnToServer("Check_Login error");
            }
        }catch (FileNotFoundException | NoSuchFileException e) {
            client.printlnToServer("Check_Login error");
            System.out.println("file not found");
        } catch(IOException e) {
            client.printlnToServer("Check_Login error");
            System.out.println("IO exceptions");
        } catch (SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void deleteStudentFile(String params) throws IOException, SAXException, ParserConfigurationException {
        String filename = Methods.getParam(params, "Filename");
        if(signUser.existFile(filename)){
            if(DataAccess.deleteStudentFile(filename)){
                DataAccess.deleteClientFile(signUser, filename);
                client.printlnToServer("DeleteStudentFile_accept?Filename="+filename);
            }else{
                client.printlnToServer("DeleteStudentFile_error?Some problem on the server!");
            }
        }else{
            client.printlnToServer("DeleteStudentFile_error?You haven't permission to delete or change this file!");
        }
    }

    private void addStudentFile(String params) throws ParserConfigurationException, SAXException, IOException {
        String filename = Methods.getParam(params, "Filename") + ".xml";
        if(DataAccess.addStudentFile(signUser, filename)){
            signUser.addFile(filename);
            client.printlnToServer("AddStudentFile_accept?Filename="+filename);
        }else{
            client.printlnToServer("AddStudentFile_error?File is already exist!");
        }
    }

    private void showStudentFile(String params){
        String filename = Methods.getParam(params, "Filename");
        try {
            if(signUser.existFile(filename))
                client.printlnToServer("ShowStudentFile_accept?Filename="+filename+"&FileContent="+DataAccess.getStudentFileContent(filename));
            else
                client.printlnToServer("ShowStudentFile_accept?Filename="+filename+"&FileContent="+DataAccess.getStudentFileContent(filename));
        } catch (IOException e) {
            client.printlnToServer("ShowStudentFile_error?Some problem on the server!");
        }

    }

    private void changeStudentFile(String params){
        String filename = Methods.getParam(params, "Filename");
        String fileContent = Methods.getParam(params, "FileContent");
        try {
            DataAccess.changeStudentFile(filename, fileContent);
            client.printlnToServer("ChangeStudentFile_accept?Filename="+filename+"&FileContent="+fileContent);
        } catch (IOException e) {
            client.printlnToServer("ChangeStudentFile_error?Some problem on the server!");
        }
    }

    private void getAllStudentFiles(){
        client.printlnToServer("StudentFiles?Filenames=" + DataAccess.getStudentFiles());
    }

    private void checkPermission(String params){
        String filename = Methods.getParam(params, "Filename");
        if(signUser.existFile(filename))
            client.printlnToServer("CheckPermission?Filename="+filename+"&Permission=present");
        else
            client.printlnToServer("CheckPermission?Filename="+filename+"&Permission=missing");

    }

}


