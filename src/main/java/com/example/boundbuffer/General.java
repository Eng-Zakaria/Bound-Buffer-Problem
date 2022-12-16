package com.example.boundbuffer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Objects;

public class General {
    public static int getIntFromTextField(TextField textField) {
        String text = textField.getText();
        return Integer.parseInt(text);
    }
    @FXML
    private AnchorPane anchorRoot;
    @FXML
    private StackPane parentContainer;
    @FXML
    Stage stage;
    @FXML
    Scene scene;
    @FXML
    Parent root;


    public void changeScene(ActionEvent event, String fxmlFile) throws Exception {

            try{
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFile)));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();


            } catch (Exception e) {
                e.printStackTrace();
            }

        System.out.println("Hello");
    }
}
