package com.example.boundbuffer;

import com.example.boundbuffer.Models.Ticket;
import com.example.boundbuffer.Models.Vendor;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
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
    Button imageUploadBtn;
    @FXML
    ImageView imageView;
    @FXML
    Image image;


    @FXML
    TextField ticketDescriptionTxt;
    @FXML
    Spinner<Integer> quantitySpinner;
    @FXML
    Spinner<Double> priceSpinner;
    @FXML
    DatePicker startTime;
    @FXML
    DatePicker expiryTime;

    @FXML
    TableView<Ticket> vendorTicketTV;
    @FXML
    TableColumn<Ticket,String> ticketDescription;
    @FXML
    TableColumn<Ticket,String> ticketName;
    @FXML
    TableColumn<Ticket,String> ticketType;
    @FXML
    TableColumn<Ticket,Double> ticketPrice;
    @FXML
    TableColumn<Ticket,Integer> ticketQuantity;
    @FXML
    TableColumn<Ticket, String> ticketExpiryDate;
    @FXML
    TableColumn<Ticket, String> ticketStartDate;

    @FXML
    TableView<Ticket> vendorTicketTV1;
    @FXML
    TableColumn<Ticket,String> ticketDescription1;
    @FXML
    TableColumn<Ticket,String> ticketName1;
    @FXML
    TableColumn<Ticket,String> ticketType1;
    @FXML
    TableColumn<Ticket,Double> ticketPrice1;
    @FXML
    TableColumn<Ticket,Integer> ticketQuantity1;
    @FXML
    TableColumn<Ticket, String> ticketExpiryDate1;
    @FXML
    TableColumn<Ticket, String> ticketStartDate1;

    @FXML
    Button refreshTableBtn;

    @FXML
    Label errorLabel;

    String imagePathTxt;
    String imagePathTxt1;


    @FXML
    TextField ticketTitleTxt1;
    @FXML
    TextField ticketTypeTxt1;



    @FXML
    TextField ticketDescriptionTxt1;
    @FXML
    Spinner<Integer> quantitySpinner1;
    @FXML
    Spinner<Double> priceSpinner1;
    @FXML
    DatePicker startTime1;
    @FXML
    DatePicker expiryTime1;

    @FXML
    Image image1;

    @FXML
    Button imageUploadBtn1;
    @FXML
    ImageView imageView1;
    @FXML
    Label errorLabel1;
    @FXML
    TabPane tabPane;
    @FXML
    Tab tab;




    public void back(ActionEvent event) throws IOException {

        try {


            General general = new General();
            general.changeScene(event,"hello-view.fxml");
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public void addTicket (ActionEvent event) throws IOException{

        try{
            if( image != null && imagePathTxt.endsWith(".jpg")){
                if(startTime.getValue() != null && expiryTime.getValue() != null){
                    if(!ticketTitleTxt.getText().isEmpty() && !ticketDescriptionTxt.getText().isEmpty() && !ticketTypeTxt.getText().isEmpty()){
                        LocalDate startTimeValue = startTime.getValue();//For reference
                        DateTimeFormatter formatterStart = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        String formattedStringStart = startTimeValue.format(formatterStart);

                        LocalDate expiryTimeValue = expiryTime.getValue();//For reference
                        DateTimeFormatter formatterExpiry = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        String formattedStringExpiry = expiryTimeValue.format(formatterExpiry);

                        General general = new General();
                        Vendor vendor;
                        vendor = (Vendor) general.receiveObjDataInScene(event);
                        vendor.addTicket(ticketTitleTxt.getText(),ticketTypeTxt.getText(),quantitySpinner.getValue(),priceSpinner.getValue(),imagePathTxt,ticketDescriptionTxt.getText(),formattedStringStart,formattedStringExpiry);
                    }
                    else{
                        errorLabel.setText("Please insert valid name and description");
                    }
                }
                else{
                    errorLabel.setText("Please choose valid date");
                }
            }
            else{
                errorLabel.setText("Please insert a valid image");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void editTicket (MouseEvent event) throws IOException{
        try{
            if(event.getClickCount() == 2) {
                Ticket ticket = vendorTicketTV.getSelectionModel().getSelectedItem();
                tabPane.getSelectionModel().select(tab);
                if (image1 != null && imagePathTxt1.endsWith(".jpg")) {
                    if (startTime1.getValue() != null && expiryTime1.getValue() != null) {
                        if (!ticketTitleTxt1.getText().isEmpty() && !ticketDescriptionTxt1.getText().isEmpty() && !ticketTypeTxt1.getText().isEmpty()) {
                            LocalDate startTimeValue = startTime1.getValue();//For reference
                            DateTimeFormatter formatterStart = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                            String formattedStringStart = startTimeValue.format(formatterStart);

                            LocalDate expiryTimeValue = expiryTime1.getValue();//For reference
                            DateTimeFormatter formatterExpiry = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                            String formattedStringExpiry = startTimeValue.format(formatterExpiry);

                            General general = new General();


                            ticketTitleTxt1.setText(ticket.getName());
                            ticketTypeTxt1.setText(ticket.getType());
                            priceSpinner1.setPromptText(String.valueOf(ticket.getPrice()));
                            quantitySpinner1.setPromptText(String.valueOf(ticket.getQuantity()));
                            ticketDescriptionTxt1.setText(ticket.getDescription());
                            startTime1.setAccessibleText(ticket.getStartTime());
                            expiryTime1.setAccessibleText(ticket.getEndTime());

                            ticket.setName(ticketTitleTxt1.getText());
                            ticket.setType(ticketTypeTxt1.getText());
                            ticket.setQuantity(quantitySpinner1.getValue());
                            ticket.setPrice(priceSpinner1.getValue());
                            ticket.setImagePath(imagePathTxt1);
                            ticket.setDescription(ticketDescriptionTxt1.getText());
                            ticket.setStartTime(formattedStringStart);
                            ticket.setEndTime(formattedStringExpiry);

                        } else {
                            errorLabel1.setText("Please insert valid name and description");
                        }
                    } else {
                        errorLabel1.setText("Please choose valid date");
                    }
                }
            }
            else{
                errorLabel1.setText("Please insert a valid image");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void uploadTicketImage(ActionEvent event){

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
                errorLabel.setText("Please Insert a valid Image");
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void changeTicketImage(ActionEvent event){

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
                errorLabel.setText("Please Insert a valid Image");
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public void exit(ActionEvent event){
        Platform.exit();
    }



    public void showVendorTicketTableView(ActionEvent event){

        General general = new General();
        Vendor vendor;
        vendor = (Vendor) general.receiveObjDataInScene(event);

        ObservableList<Ticket> vendorTicketsCollection = FXCollections.observableArrayList(vendor.getTicketsForSellByEveryVendor());

        vendorTicketTV.setItems(vendorTicketsCollection);
        ticketName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        ticketDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        ticketType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        ticketPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        ticketQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        ticketStartDate.setCellValueFactory(new PropertyValueFactory<>("StartTime"));
        ticketExpiryDate.setCellValueFactory(new PropertyValueFactory<>("EndTime"));

    }


}

