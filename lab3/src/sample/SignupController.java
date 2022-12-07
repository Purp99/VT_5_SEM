package sample;

import java.net.URL;

import java.util.ResourceBundle;

import javafx.fxml.FXML;

import javafx.scene.control.*;

public class SignupController {

    private Client client;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField TF_Login;

    @FXML
    private PasswordField PF_Password;

    @FXML
    private PasswordField PF_RPassword;

    @FXML
    private Button Btn_Signup;

    @FXML
    void initialize() {
        Btn_Signup.setOnAction(actionEvent -> {
            if(checkLogin())
                if(checkPassword())
                    client.printlnToServer("CheckLogin?Login=" + TF_Login.getText());
        });

    }

    public void handleServerAnswerCheckLogin(boolean checkLoginRes){
        if(checkLoginRes){
            client.printlnToServer("Signup?Login=" + TF_Login.getText() + "&Password=" + PF_Password.getText());
            TF_Login.setText("");
            PF_Password.setText("");
            PF_RPassword.setText("");
            SigninController.closeSignup();
        }else
            showMes(Alert.AlertType.ERROR, "Error", "Login error!", "Please choose another login and fill it again.");
    }
    public void handleServerAnswerSignUp(boolean signupRes){
        if(signupRes){
            showMes(Alert.AlertType.INFORMATION, "Information", "Signup completed successfully.", null);
        }else
            showMes(Alert.AlertType.ERROR, "Server Error", "Signup completed unsuccessfully.", null);
    }

    public void initData(Client client) {
        this.client = client;
    }

    private boolean checkPassword(){
        if(PF_Password.getText().length() < 8 && PF_RPassword.getText().length() < 8){
            showMes(Alert.AlertType.ERROR, "Error", "Password length error!", "Password length should be equal or more than 8.");
        }else{
            if(!PF_Password.getText().equals(PF_RPassword.getText())){
                PF_Password.setText("");
                PF_RPassword.setText("");
                showMes(Alert.AlertType.ERROR, "Error", "Password math error!", "Passwords should be equals! Please fill your passwords again and check match.");
            }else
                return true;
        }
        return false;
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
        if(content != null)
            alert.setContentText(content);
        alert.show();
    }

    public boolean isFieldsEmpty(){
        return TF_Login.getText().equals("") && PF_Password.getText().equals("") && PF_RPassword.getText().equals("");
    }

    public void onClose(){
        TF_Login.setText("");
        PF_RPassword.setText("");
        PF_Password.setText("");
    }
}
