package com.example.boundbuffer;

import com.example.boundbuffer.Models.BoundBuffer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.scene.*;
import java.io.IOException;
import java.util.Objects;

public class SignupCustomerController {

    @FXML
    TextField customerNameTxt;
    @FXML
    TextField emailTxt;
    @FXML
    PasswordField passwordTxt;
    @FXML
    TextField balanceTxt;
    @FXML
    Button confirmSignupVendorBtn;
    @FXML
    Button backToLoginBtn;
    @FXML
    javafx.scene.control.Label invalidDataLabel;

    @FXML
     Stage stage;
    @FXML
     Scene scene;
    @FXML
    private Parent root;

    // String name,String email,String password,double balance

    public void signUp(ActionEvent event) throws IOException {
        try {
            if (!(customerNameTxt.getText().equals("") || emailTxt.getText().equals("") || passwordTxt.getText().equals("") || balanceTxt.getText().equals("") )) {
                //String nameOfStore,String username,String password,String imagepath,String des
                String[] data = {customerNameTxt.getText(), emailTxt.getText(), passwordTxt.getText(), balanceTxt.getText()};

                BoundBuffer.signUp(1, data);

                General general = new General();
                general.changeScene(event, "vendor-view.fxml");
            } else {

                invalidDataLabel.setText("Please Enter Valid Data");

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void backToLogin(ActionEvent event) throws Exception {

        General general = new General();
        general.changeScene(event,"hello-view");
    }

}
