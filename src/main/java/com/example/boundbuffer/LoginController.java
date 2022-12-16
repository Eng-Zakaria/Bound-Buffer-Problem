package com.example.boundbuffer;

import com.example.boundbuffer.Models.BoundBuffer;
import com.example.boundbuffer.Models.Customer;
import com.example.boundbuffer.Models.Vendor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextArea emailUsername;
    @FXML
    private TextArea password;
    @FXML
    private Button loginBtn;
    @FXML
    private Button signUpBtn;

    Customer customer;
    Vendor vendor;


    public void signup(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Sign_up.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void login(ActionEvent event) throws IOException {
        BoundBuffer boundBuffer = new BoundBuffer();
        System.out.println(emailUsername.getText());
        boundBuffer.login(emailUsername.getText(), password.getText());

        if(emailUsername.getText().contains("@") ){
            customer = (Customer) boundBuffer;
            vendor = null;
            try{
                Parent root = FXMLLoader.load(getClass().getResource("customer.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            catch (Exception e){
                e.printStackTrace();
            }


        } else if (!emailUsername.getText().contains("@")) {
            vendor = (Vendor) boundBuffer;
            customer = null;
            Parent root = FXMLLoader.load(getClass().getResource("vendor.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();


        }





    }

}
