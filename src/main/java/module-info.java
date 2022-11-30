module com.example.boundbuffer {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.boundbuffer to javafx.fxml;
    exports com.example.boundbuffer;
    exports com.example.boundbuffer.Models;
    opens com.example.boundbuffer.Models to javafx.fxml;
    exports com.example.boundbuffer.Controllers;
    opens com.example.boundbuffer.Controllers to javafx.fxml;
}