package com.example.boundbuffer;
import com.example.boundbuffer.Models.BoundBuffer;
import com.example.boundbuffer.Models.Customer;
import com.example.boundbuffer.Models.Vendor;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;


import java.awt.event.ActionEvent;
import java.util.concurrent.*;

public class MultiThreading extends Application implements Runnable {
    @FXML
    Button RunBtn;
    @FXML
    Spinner<Integer> spinnerCustomer;
    @FXML
    Spinner<Integer> spinnerVendor;
    @FXML
    Spinner<Integer> createTickets;

    @FXML
    javafx.scene.control.TextArea textAre;


    @Override
    public void run( ) {




    }
    public void runBtn(ActionEvent event){

        for (int i =0 ; i<spinnerCustomer.getValue(); i++ ) {
            BoundBuffer.customers.add(new Customer("customer" + i, "customerEmail" + i + "@gmail.com", "c1234" + i, 10000.0, 1));
        }
        for(int i = 0; i<spinnerVendor.getValue(); i++){
            BoundBuffer.vendors.add( new Vendor("vendor"+i,"vendorUser"+i,"v1234"+i, "abcd"+i, "abcd",1));
        }



        createTickets.getValue();

    }
    public static void main(String[]args){



        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                // High-priority thread code goes here
            }
        });

        t1.setPriority(Thread.MAX_PRIORITY);
        t1.start();



    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}












