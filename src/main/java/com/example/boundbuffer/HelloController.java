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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Pair;

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
    PasswordField passwordTextField;
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

     /*
    * to revice output from this function you have to make
    * Pair p = boundbuffer.login(...boxusername..,.boxpassword.....);
    *     p.getKey() that's for BoundBuffer returned from login fun
    *    p.getValue() that's for type of error in function
    *   types of error
    *     لو القيمة بتاعت ()p.getValue بتساوي
    *    -1 ->incorrect userName or email OR password
    *    -2 ->there is a something happened in read your data MAY be its fault data
    *    -3 ->customer is ale

*    -3 ->customer is aleardy login from another device (sorry dude, you cannt login from multiple devices)
    *    -4 ->customer is aleardy login, but we did NOT check for password yet so we will infrom customer that there is something happend while trying to login
    *
    *
    * !!   only condation to login in correclty   !!
    *    p.getKey()   is Refrence for boundbuffer
    *    Customer customer = (Customer)p.getKey();
    *    or
    *   Vendor vendor = (Vendor) p.getKey();
    *
*  , p.getValue() is 1
    *
    *
     *
    *
    *
    */



    public void changeToLoginView(ActionEvent event){
        BoundBuffer boundBuffer = new BoundBuffer();

        System.out.println(usernameTextField.getText());
        Pair<BoundBuffer, Integer> pair =  boundBuffer.login(usernameTextField.getText(), passwordTextField.getText());
        if(pair.getKey() == null){
            if(pair.getValue()==-1){
                invalidInputLabel.setText("incorrect userName or email OR password");

            } else if (pair.getValue()==-2) {
                invalidInputLabel.setText("there is a something happened in read your data MAY be its fault data");


            } else if (pair.getValue()==-3) {

                invalidInputLabel.setText("customer is aleardy login from another device (sorry dude, you cannt login from multiple devices)");

            } else if (pair.getValue()==-4){
                invalidInputLabel.setText("customer is aleardy login, but we did NOT check for password yet so we will infrom customer that there is something happend while trying to login");

            }
        }
        else{
            if(usernameTextField.getText().contains("@") ){
                customer = (Customer) pair.getKey();
                vendor = null;
                try{
                    General general = new General();
                    general.sendAndSwitch(event,"customer-view.fxml",customer);
                }
                catch (Exception e){
                    e.printStackTrace();
                }


            } else if (!usernameTextField.getText().contains("@")) {

                vendor = (Vendor)  pair.getKey();
                customer = null;
                try{
                    General general = new General();
                    general.sendAndSwitch(event,"vendor-view.fxml",vendor);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println("Hello");

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