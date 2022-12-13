package com.example.boundbuffer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {



    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setResizable(false);
        stage.setTitle("Main");
        // stage.getIcons().add(new Image("E:\\datascience\\pro\\src\\main\\resources\\com\\example\\boundbuffer\\images \\soccer-icon.png"));
        stage.setScene(scene);
        stage.show();


    }


    public static void main(String[] args) {

        launch();
    }


}