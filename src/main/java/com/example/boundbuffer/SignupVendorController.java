package com.example.boundbuffer;

import com.example.boundbuffer.Models.BoundBuffer;
import com.example.boundbuffer.Models.Vendor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.scene.control.TextField;

import java.io.File;
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
    Image image;

    String imagePathTxt;
    @FXML
    TextArea descriptionTxt;
    @FXML
    Button confirmSignupVendorBtn;
    @FXML
    Button backToLoginBtn;
    @FXML
    Label invalidDataLabel;
    @FXML
    ImageView imageView;



    public void addVendor(String nameOfStore,  String username,String password , String imagePath,String description ){
        Vendor vendor = new Vendor(nameOfStore, username, password, imagePath ,description, 1);

    }

    public void addVendor2(){

    }
    public void uploadVendorImage(ActionEvent event){

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image File","*.png","*.jpg"));

        File file = fileChooser.showOpenDialog(stage);

        try {
            if (file !=null){
                imagePathTxt = file.getPath();
                image = new Image(file.getPath());
                imageView.setImage(image);
            }
            else{
                invalidDataLabel.setText("Please Insert a valid Image");
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void signUpVendor(ActionEvent event) throws IOException {

        try {
            if( image != null && imagePathTxt.endsWith(".jpg")) {
                if (!storeNameTxt.getText().isEmpty() && !usernameTxt.getText().isEmpty() && !passwordTxt.getText().isEmpty() && !imagePathTxt.isEmpty() && !descriptionTxt.getText().isEmpty()) {
                    //String nameOfStore,String username,String password,String imagepath,String des
                    String[] data = {storeNameTxt.getText(), usernameTxt.getText(), passwordTxt.getText(), imagePathTxt, descriptionTxt.getText()};

                    BoundBuffer.signUp(2, data);
                    General general = new General();
                    general.changeScene(event, "hello-view.fxml");
                } else {

                    invalidDataLabel.setText("Please Enter Valid Data");

                }
            }
            else{
                invalidDataLabel.setText("Please insert Valid");
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
