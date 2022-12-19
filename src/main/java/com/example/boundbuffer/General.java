package com.example.boundbuffer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.Objects;

public class General {

    @FXML
    Stage stage;
    @FXML
    Scene scene;
    @FXML
    Parent root;


    public void changeScene(ActionEvent event, String fxmlFile) throws Exception {

            try{

                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.close();
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFile)));

                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();


            } catch (Exception e) {
                e.printStackTrace();
            }

        System.out.println("Hello");
    }
    public void sendAndSwitch(ActionEvent event, String fxmlFile, Object obj) throws Exception {

        try{

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.close();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFile)));
            stage.setUserData(obj);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Hello");
    }

    public void sendObjectInScene(MouseEvent event, Object obj){

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
        stage.setUserData(obj);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public Object receiveObjDataInScene(ActionEvent event){
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        return stage.getUserData();

    }


}
