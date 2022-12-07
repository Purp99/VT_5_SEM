package sample.Server;


import java.util.ArrayList;
import java.util.List;

public class UserData {

    private static int idCounter = 0;
    private String id;
    private final String login;
    private final String password;
    private List<String> files = new ArrayList<>();

    public UserData(String login, String password) {
        this.id = String.valueOf(idCounter);
        this.login = login;
        this.password = password;
    }

    public static void updateID(){
        idCounter++;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addFile(String filename){
        files.add(filename);
    }

    public boolean existFile(String filename){
        return files.contains(filename);
    }

    public void deleteFile(String filename){
        files.remove(filename);
    }

    public static void setIdCounter(int idCounter) {
        UserData.idCounter = idCounter;
    }
}
