package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

import java.util.ArrayList;
import java.util.List;

public class ArchiveController {

    static class StudentFile{
        private final String name;
        private String description;

        public StudentFile(String name) {
            this.name = name;
            this.description = null;
        }
    }

    private static List<StudentFile> studentFiles = new ArrayList<>();
    private static Client client;
    private static ObservableList<String> fileList = FXCollections.observableArrayList();
    private static final KeyCombination keyCombination = new KeyCodeCombination(KeyCode.S , KeyCombination.CONTROL_DOWN);

    @FXML
    private ListView LV_Files;

    @FXML
    private TextArea TA_Description;

    @FXML
    private Button Btn_Add;

    @FXML
    private Button Btn_Delete;

    @FXML
    private TextField TF_Filename;

    @FXML
    void initialize() {

        LV_Files.setItems(fileList);
        TA_Description.setEditable(false);

        Btn_Delete.setOnAction(actionEvent -> {
            String selected = getSelectedItem();
            client.printlnToServer("DeleteStudentFile?Filename=" + selected + "&Index=" + fileList.indexOf(selected));
        });

        Btn_Add.setOnAction(actionEvent -> {
            if (checkFilename()){
                client.printlnToServer("AddStudentFile?Filename="+ TF_Filename.getText());
                TF_Filename.setText("");
            }else{
                showMes(Alert.AlertType.ERROR, "Error", "Filename error!", "Filename should consist only eng/ru letters and digits.");
            }

        });

        LV_Files.setOnMouseClicked(mouseEvent -> {

                TA_Description.setText("");
                String selected = getSelectedItem();
                if(selected != null) {
                    StudentFile file = studentFiles.get(getIndexByName(selected));
                    client.printlnToServer("CheckPermission?Filename=" + selected);
                    if (file.description == null) {
                        client.printlnToServer("ShowStudentFile?Filename=" + selected);
                    }
                    else {
                        TA_Description.setText(file.description);
                    }
                }
        });

        TA_Description.setOnKeyReleased(keyEvent -> {
            if (keyCombination.match(keyEvent)){
               String selected = getSelectedItem();
                if(!studentFiles.get(getIndexByName(selected)).description.equals(TA_Description.getText())){
                    client.printlnToServer("ChangeStudentFile?Filename="+selected+"&FileContent="+TA_Description.getText());
                }

            }
        });
    }

    public void handleServerAnswerDelete(boolean deleteRes, String answer){
        if(deleteRes){
            String filename = Methods.getParam(answer, "Filename");
            studentFiles.remove(getIndexByName(filename));
            fileList.remove(filename);
            TA_Description.setText("");
            showMes(Alert.AlertType.INFORMATION, "Information", "File was deleted correctly.", null);
        }else{
            showMes(Alert.AlertType.ERROR, "Server error", answer, null);
        }


    }

    public void handleServerAnswerChange(boolean changeRes, String answer){
        if(changeRes){
            studentFiles.get(getIndexByName(Methods.getParam(answer, "Filename"))).description = Methods.getParam(answer, "FileContent");
            showMes(Alert.AlertType.INFORMATION, "Information", "File was saved correctly.", null);
        }else{
            showMes(Alert.AlertType.ERROR, "Server error", answer, null);
        }
    }

    public void handleServerAnswerCheckPermission(String answer){
        String permission = Methods.getParam(answer, "Permission");
        TA_Description.setEditable(permission.equals("present"));
    }



    public void handleServerAnswerShow(boolean showRes, String answer){
        if(showRes){
            StudentFile file = studentFiles.get(getIndexByName(Methods.getParam(answer, "Filename")));
            file.description = Methods.getParam(answer, "FileContent");
            TA_Description.setText(Methods.getParam(answer, "FileContent"));
        }else{
            showMes(Alert.AlertType.ERROR, "Server error", answer, null);
        }


    }

    public void handleServerAnswerAdd(boolean addRes, String answer){
        if(addRes){
            String filename = Methods.getParam(answer, "Filename");
            fileList.add(filename);
            studentFiles.add(new StudentFile(filename));
        }else{
            showMes(Alert.AlertType.ERROR, "Server error", answer, null);
        }


    }

    public void initData(Client client){
        this.client = client;
        client.printlnToServer("GetAllStudentFiles?");
    }

    public void setStudentFiles(StringBuilder serverAnswer){
        String fileNames = serverAnswer.substring(serverAnswer.lastIndexOf("=")+1);
        if(!fileNames.equals("")) {
            String[] lines = fileNames.split("&");
            for (String line : lines) {
                studentFiles.add(new StudentFile(line));
                fileList.add(line);
            }
        }
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

    private boolean checkFilename(){
        return TF_Filename.getText().matches("[a-zA-Zа-яА-Я0-9]+");
    }
    private int getIndexByName(String name){
        for (StudentFile sf : studentFiles){
            if (sf.name.equals(name)){
                return studentFiles.indexOf(sf);
            }
        }
        return -1;
    }

    private String getSelectedItem(){
        try {
            MultipleSelectionModel msm = LV_Files.getSelectionModel();
            ObservableList list = msm.getSelectedItems();
            if (list.size() > 0) {
                if (msm.getSelectedIndex() > -1) {
                    return (String) list.get(0);
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void onClose(){
        fileList.clear();
        studentFiles.clear();
        TA_Description.setText("");
        TF_Filename.setText("");
        TA_Description.setEditable(true);

    }
}
