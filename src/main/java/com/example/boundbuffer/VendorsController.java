package com.example.boundbuffer;

import com.example.boundbuffer.Models.Vendor;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class VendorsController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    Button addTicketBtn;
    @FXML
    Button backToLogin;
    @FXML
    Button Exit;
    @FXML
    TextField ticketTitleTxt;
    @FXML
    TextField ticketTypeTxt;
    @FXML
    TextField imagePathTxt;
    @FXML
    TextField ticketDescriptionTxt;
    @FXML
    Spinner<Integer> quantitySpinner;
    @FXML
    Spinner<Integer> priceSpinner;
    @FXML
    DatePicker startTime;
    @FXML
    DatePicker expiryTime;


    Vendor vendor;

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;

    }
    public Vendor getVendor(Vendor vendor){
        return vendor;
    }

    public void back(ActionEvent event) throws IOException {

        try {

//            Parent root = FXMLLoader.load(getClass().getResource("log_in.fxml"));
//            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//            scene = new Scene(root);
//            stage.setScene(scene);
//            stage.show();

            General general = new General();
            general.changeScene(event,"hello-view.fxml");
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public void addTicket (ActionEvent event) throws IOException{

        LocalDate startTimeValue = startTime.getValue();//For reference
        DateTimeFormatter formatterStart = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedStringStart = startTimeValue.format(formatterStart);

        LocalDate expiryTimeValue = expiryTime.getValue();//For reference
        DateTimeFormatter formatterExpiry = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedStringExpiry = startTimeValue.format(formatterExpiry);

        System.out.println("Vendor is passed");
        vendor.addTicket(ticketTitleTxt.getText(),ticketTypeTxt.getText(),quantitySpinner.getValue(),priceSpinner.getValue(),imagePathTxt.getText(),ticketDescriptionTxt.getText(),formattedStringStart,formattedStringExpiry);
        System.out.println("Vendor is passed again");
        System.out.println(vendor);

//(String name, String type,int quantity ,double price, String imagepath,String description,String startTime,String EndTime)


    }


    public void exit(ActionEvent event){
        Platform.exit();
    }



}
