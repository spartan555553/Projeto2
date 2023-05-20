package Controllers;

import Entity.Enchido;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FaseProducao implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TableView<Enchido> enchidoView;

    @FXML
    private TableColumn<Enchido, Integer> idEnchidoColumn;

    @FXML
    private TableColumn<Enchido, String> tipoEnchidoColumn;

    @FXML
    private TableColumn<Enchido, Double> custoUnitarioColumn;

    @FXML
    private TableColumn<Enchido, String> descricaoColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureTableColumns();
        populateEnchidoTable();
    }

    private void configureTableColumns() {
        // Configure the TableColumn instances with the desired attribute names
        TableColumn<Enchido, String> tipoEnchidoColumn = new TableColumn<>("Tipo Enchido");
        TableColumn<Enchido, Double> custoUnitárioColumn = new TableColumn<>("Custo Unitário");
        TableColumn<Enchido, String> descriçãoColumn = new TableColumn<>("Descrição");

        // Configure the PropertyValueFactory instances to extract attribute values
        tipoEnchidoColumn.setCellValueFactory(new PropertyValueFactory<>("tipoEnchido"));
        custoUnitárioColumn.setCellValueFactory(new PropertyValueFactory<>("custoUnitário"));
        descriçãoColumn.setCellValueFactory(new PropertyValueFactory<>("descrição"));

        // Add the configured TableColumn instances to the TableView
        enchidoView.getColumns().add(tipoEnchidoColumn);
        enchidoView.getColumns().add(custoUnitárioColumn);
        enchidoView.getColumns().add(descriçãoColumn);
    }

    private void populateEnchidoTable() {
        ObservableList<Enchido> enchidos = FXCollections.observableArrayList();
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "SELECT * FROM \"Enchido\"";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String tipoEnchido = rs.getString("tipo_enchido");
                double custoUnitário = rs.getDouble("custo_unitário");
                String faseProducao = rs.getString("fase produção");
                String descricao = rs.getString("descrição");
                Integer quantidade = rs.getInt("quantidade");

                Enchido enchido = new Enchido(tipoEnchido, custoUnitário, faseProducao, descricao, quantidade);
                enchidos.add(enchido);
            }

            enchidoView.setItems(enchidos);

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
