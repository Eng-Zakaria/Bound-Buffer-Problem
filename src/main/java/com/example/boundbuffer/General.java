package com.example.boundbuffer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.lang.reflect.Method;
import java.util.Objects;

public class General {
    public static int getIntFromTextField(TextField textField) {
        String text = textField.getText();
        return Integer.parseInt(text);
    }
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

//                stage.setUserData(vendor);

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

    public EventHandler<MouseEvent> dblclickDescription = new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent event) {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(event.getClickCount() == 2){
                   //Statement
                }
            }
            event.consume();
        }
    };
//    @FXML
//    private void sendVendor(Vendor vendor ) {
//        // Step 1
//
//        // Step 2
//        Node node = (Node) event.getSource();
//        // Step 3
//        Stage stage = (Stage) node.getScene().getWindow();
//        stage.close();
//        try {
//            // Step 4
//            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/SceneA.fxml"));
//            // Step 5
//            stage.setUserData(vendor);
//            // Step 6
//            Scene scene = new Scene(root);
//            stage.setScene(scene);
//            // Step 7
//            stage.show();
//        } catch (IOException e) {
//            System.err.println(String.format("Error: %s", e.getMessage()));
//        }
//    }

    public void send(){

    }
}
