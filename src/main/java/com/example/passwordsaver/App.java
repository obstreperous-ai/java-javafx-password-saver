package com.example.passwordsaver;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/MainView.fxml"));
        Scene scene = new Scene(loader.load(), 960, 600);
        scene.getStylesheets().add("/styles/app.css");

        stage.setTitle("Password Saver");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
