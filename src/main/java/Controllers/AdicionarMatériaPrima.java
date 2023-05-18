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


public class AdicionarMatériaPrima implements Initializable {

    private Map<String, Integer> fornecedorMap = new HashMap<>();
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField tipoMatériaPrimaField;
    @FXML
    private TextField quantidadeField;
    @FXML
    private TextField custoUnitárioField;
    @FXML
    private ChoiceBox fornecedorDropdown;
    @FXML
    private DatePicker dataValidadePicker;
    @FXML
    private TextField descriptionField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateFornecedorDropdown();
    }

    // Method to create a new MatériaPrima
    public void handleAdicionarMatériaPrima(ActionEvent event) {
        // Create a new instance of DatabaseConnection
        DatabaseConnection connection = new DatabaseConnection();

        //Matéria Prima attributes
        String fornecedorSelection = (String) fornecedorDropdown.getValue();
        int idFornecedor = fornecedorMap.get(fornecedorSelection);
        String tipoMatériaPrima = tipoMatériaPrimaField.getText();
        double custoUnitário = 0.0; // Default value
        try {
            custoUnitário = Double.parseDouble(custoUnitárioField.getText());
            // Use the custoUnitário value
        } catch (NumberFormatException e) {
            // Handle the case where the value is not a valid double
            System.out.println("Invalid custoUnitário value");
        }
        String descrição = descriptionField.getText();

        //Matéria-prima_lote attributes

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
        MatériaPrima matériaPrima = new MatériaPrima(idFornecedor, tipoMatériaPrima, custoUnitário, descrição);

        // Save the Matéria Prima object to the database
        if (insertMatériaPrima(matériaPrima)) {
            System.out.println("Matéria Prima inserted successfully!");

            // Get the generated idMatériaPrima from the database
            int idMatériaPrima = connection.getGeneratedId("\"matéria_prima_id_seq\"");
            System.out.println(idMatériaPrima);

            // Create a new Lote object
            Lote lote = new Lote(idFuncionário, custo, dataCriação, dataValidade, estado_lote);

            // Save the Lote object to the database
            if (insertLote(lote)) {
                System.out.println("Lote inserted successfully!");

                // Get the generated idLote from the database
                int idLote = connection.getGeneratedId("\"lote_id_seq\"");
                System.out.println(idLote);

                // Create a new MatériaPrimaLote object
                MatériaPrimaLote matériaPrimaLote = new MatériaPrimaLote(idMatériaPrima, idLote, quantidade);

                // Save the MatériaPrimaLote object to the database
                if (insertMatériaPrimaLote(matériaPrimaLote)) {
                    System.out.println("Matéria Prima Lote inserted successfully!");
                } else {
                    System.out.println("Failed to insert Matéria Prima Lote.");
                }
            } else {
                System.out.println("Failed to insert Lote.");
            }
        } else {
            System.out.println("Failed to insert Matéria Prima.");
        }

        // Close the database connection
        connection.closeConnection();
    }

    // Insert the new MatériaPrima object into the database
    private boolean insertMatériaPrima(MatériaPrima matériaPrima) {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "INSERT INTO \"Matéria-prima\" (id_fornecedor, tipo_matéria_prima, custo_unitário, descrição) VALUES (?, ?, ?, ?)";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, matériaPrima.getIdFornecedor());
            stmt.setString(2, matériaPrima.getTipoMatériaPrima());
            stmt.setDouble(3, matériaPrima.getCustoUnitário());
            stmt.setString(4, matériaPrima.getDescrição());
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

    // Insert the new Lote object into the database
    private boolean insertMatériaPrimaLote(MatériaPrimaLote matériaprimalote) {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "INSERT INTO \"Matéria-prima_Lote\" (id_matéria_prima, id_lote, quantidade) VALUES (?, ?, ?)";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, matériaprimalote.getIdMatériaPrima());
            stmt.setInt(2, matériaprimalote.getIdLote());
            stmt.setInt(3, matériaprimalote.getQuantidade());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            connection.closeConnection();
        }
    }


    // Method to populate the fornecedorDropdown ComboBox
    private void populateFornecedorDropdown() {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "SELECT * FROM \"Fornecedor\"";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            ObservableList<String> nomesFornecedores = FXCollections.observableArrayList();

            while (rs.next()) {
                int idFornecedor = rs.getInt("id_fornecedor");
                String nomeFornecedor = rs.getString("nome");

                // Add the nome to the list
                nomesFornecedores.add(nomeFornecedor);

                // Store the mapping of nome to idFornecedor in the map
                fornecedorMap.put(nomeFornecedor, idFornecedor);
            }

            // Set the items of the ComboBox with the Fornecedor objects
            fornecedorDropdown.setItems(nomesFornecedores);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
        }
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
