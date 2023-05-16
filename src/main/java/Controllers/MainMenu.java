package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenu {

    private Stage stage;
    private Scene scene;
    private Parent root;

    //Adicionar Matéria Prima Button
    @FXML
    public void handleAdicionarMatériaPrima(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/AdicionarMatériaPrima.fxml"));
        switchScene(event);
    }

    //Adicionar Enchidos Button
    @FXML
    public void handleAdicionarEnchido(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/AdicionarEnchido.fxml"));
        switchScene(event);
    }

    //Fase Produção Button
    @FXML
    public void handleFaseProdução(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/FaseProdução.fxml"));
        switchScene(event);
    }

    //Listar Matérias Primas Button
    @FXML
    public void handleListarMatériasPrimas(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/ListarMatériasPrimas.fxml"));
        switchScene(event);
    }

    //Listar Enchidos Button
    @FXML
    public void handleListarEnchidos(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/ListarEnchidos.fxml"));
        switchScene(event);
    }

    //Adicionar Fornecedor Button
    @FXML
    public void handleAdicionarFornecedor(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/AdicionarFornecedor.fxml"));
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