package com.example.boundbuffer;

import com.example.boundbuffer.Models.BoundBuffer;
import com.example.boundbuffer.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomersController {

    private Stage stage;
    private Scene scene;
    private Parent root;



    @FXML
    Button Button_Back;
    @FXML
    TableView Customer_table;
    @FXML
    Button buypageid;
    @FXML
    Label Ticket_num;

    public void back(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void buypage(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("buypage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }




}
