package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class Login {

    @FXML
    private TextField UsernameField;
    @FXML
    private PasswordField PasswordField;

    private Stage stage;
    private Scene scene;
    private Parent root;

    //Login Method
    @FXML
    public void handleLogin(ActionEvent event) throws IOException {
        String username = UsernameField.getText();
        String password = PasswordField.getText();

        if (authenticate(username, password)) {
            // Successful login
            // Navigate to the main menu or another screen
            root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
            switchScene(event);
        } else {
            // Failed login
            showAlert(Alert.AlertType.ERROR, "Login Error", "Invalid username or password.");
        }
    }

    //Database Connection
    private boolean authenticate(String username, String password) {
        DatabaseConnection connection = new DatabaseConnection();

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM \"Funcionário\" WHERE username = ? AND password = ?")) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connection.closeConnection();
        }
    }

    //Quit Button
    @FXML
    public void handleQuit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    //Method to switch scenes
    private void switchScene(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Wrong username and password alert
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
