package com.example.boundbuffer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;



public class TicketSellersController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    Button Button_Back,add_button;

    public void back(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("log_in.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void add (ActionEvent event) throws IOException{



    }

}
