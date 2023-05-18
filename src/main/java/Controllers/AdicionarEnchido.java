package Controllers;

import Entity.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import java.net.URL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class AdicionarEnchido implements Initializable {

    private Map<String, Integer> fornecedorMap = new HashMap<>();
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField tipoEnchidoField;
    @FXML
    private TextField quantidadeField;
    @FXML
    private TextField custoUnitárioField;
    @FXML
    private ChoiceBox faseProduçãoDropdown;
    @FXML
    private DatePicker dataValidadePicker;
    @FXML
    private TextField descriptionField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateFaseProduçãoDropdown();
    }

    // Method to create a new MatériaPrima
    public void handleAdicionarEnchido(ActionEvent event) {
        // Create a new instance of DatabaseConnection
        DatabaseConnection connection = new DatabaseConnection();

        //Enchido attributes

        String tipoEnchido = tipoEnchidoField.getText();
        double custoUnitário = 0.0; // Default value
        try {
            custoUnitário = Double.parseDouble(custoUnitárioField.getText());
            // Use the custoUnitário value
        } catch (NumberFormatException e) {
            // Handle the case where the value is not a valid double
            System.out.println("Invalid custoUnitário value");
        }
        String descrição = descriptionField.getText();

        //Enchido_lote attributes

        int quantidade = 0; // Default value
        try {
            quantidade = Integer.parseInt(quantidadeField.getText());
            // Use the quantidade value
        } catch (NumberFormatException e) {
            // Handle the case where the value is not a valid integer
            System.out.println("Invalid quantidade value");
        }

        //Lote attributes

        Funcionário loggedInFuncionário = Login.getLoggedInFuncionário();
        int idFuncionário = loggedInFuncionário.getIdFuncionário();
        double custo = quantidade * custoUnitário;

        // Get the current date
        java.util.Date currentDate = new java.util.Date();
        // Convert currentDate to java.sql.Date
        java.sql.Date dataCriação = new java.sql.Date(currentDate.getTime());

        LocalDate selectedDate = dataValidadePicker.getValue();
        // Convert LocalDate to java.sql.Date
        java.sql.Date dataValidade = java.sql.Date.valueOf(selectedDate);

        String estado_lote = "Ativo";


        // Create a new Matéria Prima object
        Enchido enchido = new Enchido(tipoEnchido, custoUnitário, descrição);

        // Save the Matéria Prima object to the database
        if (insertEnchido(enchido)) {
            System.out.println("Enchido inserted successfully!");

            // Get the generated idMatériaPrima from the database
            int idEnchido = connection.getGeneratedId("\"enchido_id_seq\"");
            System.out.println(idEnchido);

            // Create a new Lote object
            Lote lote = new Lote(idFuncionário, custo, dataCriação, dataValidade, estado_lote);

            // Save the Lote object to the database
            if (insertLote(lote)) {
                System.out.println("Lote inserted successfully!");

                // Get the generated idLote from the database
                int idLote = connection.getGeneratedId("\"lote_id_seq\"");
                System.out.println(idLote);

                // Create a new EnchidoLote object
                EnchidoLote enchidoLote = new EnchidoLote(idEnchido, idLote, quantidade, faseProdução);

                // Save the EnchidoLote object to the database
                if (insertEnchidoLote(enchidoLote)) {
                    System.out.println("Enchido Lote inserted successfully!");
                } else {
                    System.out.println("Failed to insert Enchido Lote.");
                }
            } else {
                System.out.println("Failed to insert Lote.");
            }
        } else {
            System.out.println("Failed to insert Enchido.");
        }

        // Close the database connection
        connection.closeConnection();
    }

    // Insert the new Enchido object into the database
    private boolean insertEnchido(Enchido enchido) {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "INSERT INTO \"Enchido\" (tipo_enchido, custo_unitário, descrição) VALUES (?, ?, ?)";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, enchido.getTipoEnchido());
            stmt.setDouble(2, enchido.getCustoUnitário());
            stmt.setString(3, enchido.getDescrição());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            connection.closeConnection();
        }
    }

    // Insert the new Lote object into the database
    private boolean insertLote(Lote lote) {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "INSERT INTO \"Lote\" (id_funcionário, custo, data_criação, data_validade, estado_lote) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, lote.getIdFuncionário());
            stmt.setDouble(2, lote.getCusto());
            stmt.setDate(3, lote.getDataCriação());
            stmt.setDate(4, lote.getDataValidade());
            stmt.setString(5, lote.getEstadoLote());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            connection.closeConnection();
        }
    }

    // Insert the new EnchidoLote object into the database
    private boolean insertEnchidoLote(EnchidoLote enchidoLote) {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "INSERT INTO \"Enchido_Lote\" (id_enchido, id_lote, quantidade, fase_produção) VALUES (?, ?, ?, ?)";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, enchidoLote.getIdEnchido());
            stmt.setInt(2, enchidoLote.getIdLote());
            stmt.setInt(3, enchidoLote.getQuantidade());
            stmt.setString(4, enchidoLote.get);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            connection.closeConnection();
        }
    }


    // Method to populate the faseProduçãoDropdown ChoiceBox
    private void populateFaseProduçãoDropdown() {
        ObservableList<String> options = FXCollections.observableArrayList(
                "1",
                "2",
                "3"
        );

        faseProduçãoDropdown.setItems(options);
    }

    //Go Back Button
    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        switchScene(event);
    }

    //Method to switch scenes
    private void switchScene(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
