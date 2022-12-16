package com.example.boundbuffer;

import com.example.boundbuffer.Models.BoundBuffer;
import com.example.boundbuffer.Models.Vendor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javafx.scene.control.TextField;

import java.io.IOException;

public class SignupVendorController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    TextField storeNameTxt;
    @FXML
    TextField usernameTxt;
    @FXML
    PasswordField passwordTxt;
    @FXML
    TextField imagePathTxt;
    @FXML
    TextArea descriptionTxt;
    @FXML
    Button confirmSignupVendorBtn;
    @FXML
    Button backToLoginBtn;
    @FXML
    Label invalidDataLabel;



    public void addVendor(String nameOfStore,  String username,String password , String imagePath,String description ){
        Vendor vendor = new Vendor(nameOfStore, username, password, imagePath ,description, 1);

    }

    public void addVendor2(){

    }

    public void signUpVendor(ActionEvent event) throws IOException {

        try {
            if(!(storeNameTxt.getText().equals("") || usernameTxt.getText().equals("") || passwordTxt.getText().equals("") || imagePathTxt.getText().equals("") || descriptionTxt.getText().equals(""))){
                //String nameOfStore,String username,String password,String imagepath,String des
                String [] data = {storeNameTxt.getText(), usernameTxt.getText(), passwordTxt.getText(), imagePathTxt.getText(), descriptionTxt.getText() };

                BoundBuffer.signUp(2,data);

                General general = new General();
                general.changeScene(event, "vendor-view.fxml");
            }
           else {

                invalidDataLabel.setText("Please Enter Valid Data");

            }

        }
        catch (Exception e) {
            invalidDataLabel.setText("Please Insert Valid Data");
            e.printStackTrace();
        }



    }
    public void backToLogin(ActionEvent event) throws Exception {

        try{
            General general = new General();
            general.changeScene(event,"hello-view.fxml");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }




}
