package com.example.boundbuffer;

import com.example.boundbuffer.Models.BoundBuffer;
import com.example.boundbuffer.Models.Ticket;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class CustomersController {

    @FXML
    Button Button_Back;
    @FXML
    TableView<Ticket> customerTicketTV;
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
    TableView<Ticket> customerTicketTV1;
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
    TabPane tabPane;
    @FXML
    Tab buyTicketTab;

    @FXML
    TextField ticketTitleTxt;
    @FXML
    TextField ticketTypeTxt;
    @FXML
    TextField ticketQuantityTxt;
    @FXML
    TextField ticketPriceTxt;
    @FXML
    TextField ticketDescriptionTxt;
    @FXML
    TextField ticketStartTimeTxt;
    @FXML
    TextField ticketExpiryTimeTxt;
    @FXML
    ImageView imageView;
    @FXML
    Button addCartBtn;

    @FXML
    Image image;

    String imagePathTxt;


    public void checkoutCustomerCart(ActionEvent event){

    }

    ObservableList<Ticket> customerTicketsCollection = FXCollections.observableArrayList(BoundBuffer.tickets);

    public void showCustomerTicketTableView(ActionEvent event) {

        int i = 0;
        while( i<BoundBuffer.tickets.size()){
            if(!BoundBuffer.tickets.get(i).sold()){
                customerTicketsCollection = FXCollections.observableArrayList(BoundBuffer.tickets);
                customerTicketTV.setItems(customerTicketsCollection);
                ticketName.setCellValueFactory(new PropertyValueFactory<>("Name"));
                ticketDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
                ticketType.setCellValueFactory(new PropertyValueFactory<>("Type"));
                ticketPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
                ticketQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
                ticketStartDate.setCellValueFactory(new PropertyValueFactory<>("StartTime"));
                ticketExpiryDate.setCellValueFactory(new PropertyValueFactory<>("EndTime"));
            }
            i++;
        }

    }

    public void clickOnTicket(MouseEvent event){
        if(event.getClickCount() == 2){
//            return (Ticket) event.getSource();
//            Object item = cell.getTableRow().getItem();

            Ticket ticket = customerTicketTV.getSelectionModel().getSelectedItem();

            ticketTitleTxt.setText(ticket.getName());
            ticketPriceTxt.setText(String.valueOf(ticket.getPrice()));
            ticketType.setText(String.valueOf(ticket.getType()));
            ticketQuantityTxt.setText(String.valueOf(ticket.getQuantity()));
            ticketDescriptionTxt.setText(String.valueOf(ticket.getDescription()));
            ticketStartTimeTxt.setText(String.valueOf(ticket.getStartTime()));
            ticketExpiryTimeTxt.setText(String.valueOf(ticket.getEndTime()));


            image = new Image(ticket.getImagePath());
            imageView.setImage(image);
            tabPane.getSelectionModel().select(buyTicketTab);
        }
    }
    public void setTicketInfo(Ticket ticket){
         Ticket selectedTicket = customerTicketsCollection.get(customerTicketsCollection.indexOf(ticket));
    }

    public void addToCart(ActionEvent event){

    }
    public void back(ActionEvent event) throws Exception {

        General general = new General();
        general.changeScene(event,"hello-view.fxml");

    }
    public void exit(){
        Platform.exit();
    }





}
