package com.apsoo.sistemagerenciamentorestaurante;

import com.apsoo.sistemagerenciamentorestaurante.controller.SistemaVendaRestaurante;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FeijaoPretoApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (Exception e) {
            System.out.println(e);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(FeijaoPretoApplication.class.getResource("view/venda-balcao-view.fxml"));
        fxmlLoader.setController(new SistemaVendaRestaurante());
        Scene scene = new Scene(fxmlLoader.load(), 1980, 1080);
        stage.setTitle("Restaurante Feijão Preto");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}