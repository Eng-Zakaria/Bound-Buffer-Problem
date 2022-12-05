package com.example.boundbuffer;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Log_inController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    public void signup(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Sign_up.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void home(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("ticket-seller.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
