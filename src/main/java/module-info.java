module com.example.proj2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javax.persistence;


    opens Controllers to javafx.fxml;
    exports Controllers;
}