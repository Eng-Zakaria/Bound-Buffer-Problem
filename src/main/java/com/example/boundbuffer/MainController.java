package com.example.boundbuffer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;


import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController extends Application {



     int noCustomers;
     double noVendors;

    @Override
    public void start(Stage primarystage) throws IOException {
        Group root = new Group();
        Stage stage = new Stage();
        Scene scene = new Scene(root, 800, 600, Color.BEIGE);

        Spinner<Integer> noCustomersSpinner = new Spinner<>(1,4,1);
        Spinner<Double> noVendorsSpinner = new Spinner<>(1,5,1);

        Label noCustomersLabel = new Label("Number of Customers: ");
        Label noVendorsLabel = new Label("Number of Vendors: ");

        Button rnuBtn = new Button("RUNNNNNNNN!");

        HBox hBox = new HBox(10);
        VBox vBox = new VBox();

        noVendorsSpinner.setPrefSize(75,25);
        noCustomersSpinner.setPrefSize(75,25);

        noCustomersSpinner.setEditable(true);
        noVendorsSpinner.setEditable(true);

        hBox.setPadding(new Insets(10, 10, 10, 25));
        hBox.getChildren().addAll(noCustomersLabel, noCustomersSpinner, noVendorsLabel, noVendorsSpinner);

        vBox.setPadding(new Insets(10, 10, 10, 25));
        vBox.setSpacing(400);

        vBox.getChildren().addAll(hBox, rnuBtn);

        rnuBtn.setLayoutX(300);
        rnuBtn.setLayoutY(400);

        root.getChildren().addAll(vBox);



        try {


            rnuBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    noCustomers = (int) noCustomersSpinner.getValue();
                    System.out.println(noCustomers);
                    noVendors = (double) noVendorsSpinner.getValue();
                    System.out.println(noVendors);

                }

            });


            primarystage.setScene(scene);
            primarystage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
