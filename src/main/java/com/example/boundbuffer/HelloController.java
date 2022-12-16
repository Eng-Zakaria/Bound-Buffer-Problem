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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloController {


    @FXML
    Button changeLoginBtn;
    @FXML
    Button changeSignupCustomerBtn;
    @FXML
    Button getChangeSignupVendorBtn;
    @FXML
    TextField usernameTextField;
    @FXML
    TextField passwordTextField;
    @FXML
    Label invalidInputLabel;
    @FXML
    Stage stage;
    @FXML
    Scene scene;
    @FXML
    Parent root;

    static Vendor vendor;
    static Customer customer;

    public static Vendor getVendor(){
        return vendor;
    }
    public static Customer getCustomer(){
        return customer;
    }

    public void test(ActionEvent event) throws Exception {

        try{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("vendor-view.fxml")));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

    public void test2(ActionEvent event) throws Exception {
        General general = new General();
        general.changeScene(event,"vendor-view.fxml");
    }

    public void changeToLoginView(ActionEvent event) throws IOException {
        BoundBuffer boundBuffer = new BoundBuffer();
        System.out.println(usernameTextField.getText());
        boundBuffer.login(usernameTextField.getText(), passwordTextField.getText());


        if(usernameTextField.getText().contains("@") ){
            customer = (Customer) boundBuffer;
            vendor = null;
            try{
                General general = new General();


                general.changeScene(event,"customer-view.fxml");
            }
            catch (Exception e){
                e.printStackTrace();
            }


        } else if (!usernameTextField.getText().contains("@")) {
            vendor = (Vendor) boundBuffer;
            customer = null;
            try {
                General general = new General();
                VendorsController vendorsController = new VendorsController();
                vendorsController.setVendor(vendor);
                general.changeScene(event,"vendor-view.fxml");
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }

    }
    public void changeToSignupCustomerView(ActionEvent event) throws Exception {
        General general = new General();
        general.changeScene(event,"signup-customer-view.fxml");

    }

    public void changeToSignupVendorView(ActionEvent event) throws Exception {
        General general = new General();
        general.changeScene(event,"signup-vendor-view.fxml");

    }




}