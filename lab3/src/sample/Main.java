package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Main extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/scenes/signin.fxml"));
        stage = primaryStage;
        primaryStage.setTitle("Archive");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();

    }

    public static void hideStage(){
        stage.hide();
    }

    public static void showStage(){
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }



}
