module com.example.boundbuffer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.google.zxing;
    requires com.google.zxing.javase;


    opens com.example.boundbuffer to javafx.fxml;
    exports com.example.boundbuffer;
    exports com.example.boundbuffer.Models;
    opens com.example.boundbuffer.Models to javafx.fxml;
}