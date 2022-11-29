module com.example.boundbuffer {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.boundbuffer to javafx.fxml;
    exports com.example.boundbuffer;
}