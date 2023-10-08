package ru.vsu.cs.baklanova.besiercurvefxapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ProtoCurveApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ProtoCurveApplication.class.getResource("mainwindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 650);
        stage.setTitle("Besier Curve App");
        stage.setScene(scene);
        stage.show();
    }

    public static void winMain() {
        launch();
    }
}