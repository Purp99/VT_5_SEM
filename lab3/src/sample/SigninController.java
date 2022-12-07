package sample;

import java.io.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class SigninController {

    private static Stage stageSignup;
    private static Stage stageArchive;
    private Client client;
    private SignupController signupController = null;
    private ArchiveController archiveController = null;


    private final Runnable mesFromServer = new Runnable() {
        public void run() {
            String mes;
            StringBuilder param = new StringBuilder();
            while (true){
                try {
                    mes = client.getLineFromServer();

                    if(!mes.equals("")){
                        if(mes.contains("?")){
                            param.delete(0, param.length());
                            param.append(mes.substring(mes.indexOf("?")+1));
                            mes = mes.substring(0, mes.indexOf("?"));

                        }
                        switch (mes) {
                            case "Authentication accept" -> {
                                Platform.runLater(() -> createArchiveWindow());
                                TF_Login.setText("");
                                PF_Password.setText("");
                            }
                            case "Authentication error" -> Platform.runLater(() -> showSigninMesError());
                            case "Check_Login accept" -> Platform.runLater(() -> signupController.handleServerAnswerCheckLogin(true));
                            case "Check_Login error" -> Platform.runLater(() -> signupController.handleServerAnswerCheckLogin(false));
                            case "Signup accept" -> Platform.runLater(() -> signupController.handleServerAnswerSignUp(true));
                            case "Signup error" -> Platform.runLater(() -> signupController.handleServerAnswerSignUp(false));
                            case "StudentFiles" -> Platform.runLater(() -> archiveController.setStudentFiles(param));
                            case "DeleteStudentFile_accept" -> Platform.runLater(() -> archiveController.handleServerAnswerDelete(true, param.toString()));
                            case "DeleteStudentFile_error" -> Platform.runLater(() -> archiveController.handleServerAnswerDelete(false, param.toString()));
                            case "AddStudentFile_accept" -> Platform.runLater(() -> archiveController.handleServerAnswerAdd(true, param.toString()));
                            case "AddStudentFile_error" -> Platform.runLater(() -> archiveController.handleServerAnswerAdd(false, param.toString()));
                            case "ShowStudentFile_accept" -> Platform.runLater(() -> archiveController.handleServerAnswerShow(true, param.toString()));
                            case "ShowStudentFile_error" -> Platform.runLater(() -> archiveController.handleServerAnswerShow(false, param.toString()));
                            case "ChangeStudentFile_accept" -> Platform.runLater(() -> archiveController.handleServerAnswerChange(true, param.toString()));
                            case "ChangeStudentFile_error" -> Platform.runLater(() -> archiveController.handleServerAnswerChange(false, param.toString()));
                            case "CheckPermission" -> Platform.runLater(() -> archiveController.handleServerAnswerCheckPermission(param.toString()));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };



    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField PF_Password;

    @FXML
    private TextField TF_Login;

    @FXML
    private Hyperlink HL_Signup;

    @FXML
    private Button Btn_Signin;

    @FXML
    void initialize(){
        while (true) {
            try {
                int port = 8888;
                String ip = "localhost";
                client = new Client(ip, port);
            } catch (IOException e) {
                continue;
            }
            Thread threadMesFromServer = new Thread(mesFromServer);
            threadMesFromServer.start();
            break;
        }


        HL_Signup.setOnAction(event -> createSignupWindow());

        Btn_Signin.setOnAction(actionEvent -> {
            if(checkLogin() && checkPassword()){
                client.printlnToServer("Signin?Login=" + TF_Login.getText() + "&Password=" + PF_Password.getText());
            }
        });
    }

    private void showSigninMesError(){
        showMes(Alert.AlertType.INFORMATION, "Information", "Your login or password are not correct.", null);
    }

    public static void closeSignup(){

        stageSignup.close();
        Main.showStage();
    }

    private void createSignupWindow(){
        if(signupController != null){
            stageSignup.show();
            Main.hideStage();
        }else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/signup.fxml"));
            stageSignup = new Stage();
            stageSignup.setTitle("Archive");
            stageSignup.setResizable(false);
            try {
                stageSignup.setScene(new Scene(loader.load(), 400, 300));
            } catch (IOException e) {
                e.printStackTrace();
            }

            signupController = loader.getController();
            signupController.initData(client);

            Main.hideStage();
            stageSignup.show();
            stageSignup.setOnCloseRequest(windowEvent -> {
                if (!signupController.isFieldsEmpty()) {
                    ButtonType btnYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                    ButtonType btnNo = new ButtonType("No", ButtonBar.ButtonData.NO);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", btnNo, btnYes);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Some fields are not empty. Do you want to get back to signin?");

                    Optional<ButtonType> res = alert.showAndWait();
                    if (res.orElse(btnYes) == btnNo) {
                        windowEvent.consume();
                    } else {
                        signupController.onClose();
                        Main.showStage();
                    }
                } else {
                    signupController.onClose();
                    Main.showStage();
                }
            });
        }
    }

    private void createArchiveWindow(){
        if(archiveController != null){
            archiveController.initData(client);
            stageArchive.show();
            Main.hideStage();
        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/archive.fxml"));
            stageArchive = new Stage();
            stageArchive.setTitle("Archive");
            stageArchive.setResizable(false);
            try {
                stageArchive.setScene(new Scene(loader.load(), 1000, 600));
            } catch (IOException e) {
                e.printStackTrace();
            }

            archiveController = loader.getController();
            archiveController.initData(client);

            Main.hideStage();
            stageArchive.show();
            stageArchive.setOnCloseRequest(windowEvent -> {
                archiveController.onClose();
                Main.showStage();
            });
        }

    }

    private boolean checkPassword(){
        if(PF_Password.getText().length() < 8){
            showMes(Alert.AlertType.ERROR, "Error", "Password length error!", "Password length should be equal or more than 8.");
            return false;
        }
        return true;
    }

    private boolean checkLogin(){
        if(TF_Login.getText().length() < 4){
            showMes(Alert.AlertType.ERROR, "Error", "Login length error!", "Login length should be equal or more than 4.");
            return false;
        }
        return true;
    }

    private void showMes(Alert.AlertType type, String title, String header, String content){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        if(content != null){
            alert.setContentText(content);
        }
        alert.show();
    }
}
