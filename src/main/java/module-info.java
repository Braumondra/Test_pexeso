module com.example.testpexeso {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.testpexeso to javafx.fxml;
    exports com.example.testpexeso;
}