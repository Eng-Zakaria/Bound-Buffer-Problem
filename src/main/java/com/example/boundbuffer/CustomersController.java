package com.example.boundbuffer;

import com.example.boundbuffer.Models.BoundBuffer;
import com.example.boundbuffer.Models.Customer;
import com.example.boundbuffer.Models.Ticket;
import com.example.boundbuffer.Models.Vendor;
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
    Spinner<Integer> spinnerQuantity;
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
    Tab ticketTab;
    @FXML
    Tab cartTab;

    @FXML
    Image image;

    @FXML
    Image imageQr;
    @FXML
    Image imageViewQr;

    String imagePathTxt;
    String imageQrTxt;

    Ticket ticket;

    Customer customer;

    public void setCustomer(Customer customer){
        this.customer = customer;
    }
    public Customer getCustomer(){
        return this.customer;
    }



    ObservableList<Ticket> customerTicketsCollection = FXCollections.observableArrayList(BoundBuffer.tickets);
    ObservableList<Ticket> customerTicketsCollection1;

    public void showCustomerTicketTableView(ActionEvent event) {

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
    public void showCustomerCartTableView(ActionEvent event) {


        General general = new General();
        Customer customer;
        customer = (Customer) general.receiveObjDataInScene(event);


//        customer.getCart().getQuentatiyForItemInCart(customer.getCart().getTicketsCart()[1].getName());
        customer.getCart().getQuentatiyForItemInCart(customer.getCart().getQuentatiyForEachTickets()[1]);
        customerTicketsCollection1 = FXCollections.observableArrayList(customer.getCart().getTicketsCart());
        customerTicketTV1.setItems(customerTicketsCollection1);
        ticketName1.setCellValueFactory(new PropertyValueFactory<>("Name"));
        ticketDescription1.setCellValueFactory(new PropertyValueFactory<>("Description"));
        ticketType1.setCellValueFactory(new PropertyValueFactory<>("Type"));
        ticketPrice1.setCellValueFactory(new PropertyValueFactory<>("Price"));
        ticketQuantity1.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        ticketStartDate1.setCellValueFactory(new PropertyValueFactory<>("StartTime"));
        ticketExpiryDate1.setCellValueFactory(new PropertyValueFactory<>("EndTime"));

    }


    public void clickOnTicket(MouseEvent event){
        if(event.getClickCount() == 2){

            tabPane.getSelectionModel().select(buyTicketTab);
            Ticket ticket = customerTicketTV.getSelectionModel().getSelectedItem();

            ticketTitleTxt.setText(ticket.getName());
            ticketPriceTxt.setText(String.valueOf(ticket.getPrice()));
            ticketTypeTxt.setText(String.valueOf(ticket.getType()));
            spinnerQuantity.setPromptText(String.valueOf(ticket.getQuantity()));

            ticketDescriptionTxt.setText(String.valueOf(ticket.getDescription()));
            ticketStartTimeTxt.setText(String.valueOf(ticket.getStartTime()));
            ticketExpiryTimeTxt.setText(String.valueOf(ticket.getEndTime()));


            image = new Image(ticket.getImagePath());
            imageView.setImage(image);
            setTicket(ticket);


        }
    }

    public void setTicket(Ticket t){
        ticket = t;
    }
    public Ticket getTicket(){
        return ticket;
    }

    public void addToCart(ActionEvent event){
        try{
            getTicket();
            General general = new General();
            Customer customer = (Customer) general.receiveObjDataInScene(event);

            customer.addToCart(ticket,ticket.getQuantity());

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void checkoutCustomerCart(ActionEvent event){

        try{
            ticket = getTicket();
            General general = new General();
            Customer customer = (Customer) general.receiveObjDataInScene(event);

            ticket.buy(customer, spinnerQuantity.getValue());
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }




    public void back(ActionEvent event) throws Exception {

        General general = new General();
        general.changeScene(event,"hello-view.fxml");
    }
    public void exit(){
        Platform.exit();
    }

}
