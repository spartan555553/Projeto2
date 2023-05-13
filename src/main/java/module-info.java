module com.example.proj2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javaee;
    requires java.sql;


    opens com.example.proj2 to javafx.fxml;
    exports com.example.proj2;
}