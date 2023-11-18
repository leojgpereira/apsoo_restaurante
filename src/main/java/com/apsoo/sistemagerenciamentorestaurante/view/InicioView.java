package com.apsoo.sistemagerenciamentorestaurante.view;

import com.apsoo.sistemagerenciamentorestaurante.FeijaoPretoApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InicioView implements Initializable {
    @FXML
    private Button btnVendaBalcao;
    @FXML
    private Button btnReembolso;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnVendaBalcao.setOnAction(actionEvent -> {
            Parent root;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(FeijaoPretoApplication.class.getResource("view/venda-balcao-view.fxml"));
                fxmlLoader.setController(new VendaBalcaoView());
                root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Venda Balcão");
                stage.setScene(new Scene(root, 1980, 1080));
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnReembolso.setOnAction(actionEvent -> {
            Parent root;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(FeijaoPretoApplication.class.getResource("view/reembolso-view.fxml"));
                fxmlLoader.setController(new ReembolsoView());
                root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Solicitar Reembolso");
                stage.setScene(new Scene(root, 1980, 1080));
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
