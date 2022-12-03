package com.example.boundbuffer.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    @FXML
    Button sellid;
    @FXML
    Button buyid;

    private Stage stage;

    private Scene scene;
    private Parent root;
    public void Buy (ActionEvent event)throws IOException {
        root= FXMLLoader.load(getClass().getResource("customer.fxml"));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }




    public void sell(ActionEvent event)throws IOException {
        root= FXMLLoader.load(getClass().getResource("customer.fxml"));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }





}