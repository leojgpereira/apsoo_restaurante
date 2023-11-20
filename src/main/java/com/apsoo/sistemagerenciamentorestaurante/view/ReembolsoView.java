package com.apsoo.sistemagerenciamentorestaurante.view;

import com.apsoo.sistemagerenciamentorestaurante.controller.SistemaVendaRestaurante;
import com.apsoo.sistemagerenciamentorestaurante.model.Venda;
import com.apsoo.sistemagerenciamentorestaurante.persistence.PedidoReembolsoDAO;
import com.apsoo.sistemagerenciamentorestaurante.persistence.VendaDAO;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReembolsoView implements Initializable {
    @FXML
    private Text timeText;
    @FXML
    private TableView vendasTableView;
    @FXML
    private TableColumn<Venda, Integer> pedidoColumn;
    @FXML
    private TableColumn<Venda, Date> dataColumn;
    @FXML
    private TableColumn<Venda, Time> horaColumn;
    @FXML
    private TableColumn<Venda, String> clienteColumn;
    @FXML
    private TableColumn<Venda, String> metodoPagamentoColumn;
    @FXML
    private TableColumn<Venda, Double> totalColumn;
    @FXML
    private TextField codigoDaVendaTextField;
    @FXML
    private TextField valorReembolsoTextField;
    @FXML
    private Button btnReembolso;

    private String adm = "LEANDRO";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /* Carrega o horário para o campo correspondente na interface gráfica */
        Timeline clock = new Timeline(
                new KeyFrame(
                        Duration.ZERO, e -> timeText.setText(
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
                )
                ),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

        VendaDAO vendaDAO = new VendaDAO();
        List<Venda> vendas = vendaDAO.recuperarTodos();

        atualizarListagemVendas(vendas);

        pedidoColumn.setCellFactory(new Callback<TableColumn<Venda, Integer>, TableCell<Venda, Integer>>() {
            @Override
            public TableCell<Venda, Integer> call(TableColumn<Venda, Integer> vendaIntegerTableColumn) {
                return new TableCell<Venda, Integer>() {
                    @Override
                    protected void updateItem(Integer codigoDaVenda, boolean empty) {
                        super.updateItem(codigoDaVenda, empty);

                        if(empty){
                            setText(null);
                        }else {
                            setText(String.format("#%04d", codigoDaVenda));
                            setStyle("-fx-font-weight: bold; -fx-alignment: center");
                        }
                    }
                };
            }
        });

        dataColumn.setCellFactory(new Callback<TableColumn<Venda, Date>, TableCell<Venda, Date>>() {
            @Override
            public TableCell<Venda, Date> call(TableColumn<Venda, Date> vendaDateTableColumn) {
                return new TableCell<Venda, Date>() {
                    @Override
                    protected void updateItem(Date data, boolean empty) {
                        super.updateItem(data, empty);

                        if(empty){
                            setText(null);
                        }else {
                            Locale locale = new Locale("pt", "BR");
                            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
                            setText(dateFormat.format(data));
                            setStyle("-fx-font-weight: bold; -fx-alignment: center");
                        }
                    }
                };
            }
        });

        horaColumn.setCellFactory(new Callback<TableColumn<Venda, Time>, TableCell<Venda, Time>>() {
            @Override
            public TableCell<Venda, Time> call(TableColumn<Venda, Time> vendaTimeTableColumn) {
                return new TableCell<Venda, Time>() {
                    @Override
                    protected void updateItem(Time hora, boolean empty) {
                        super.updateItem(hora, empty);

                        if(empty){
                            setText(null);
                        }else {
                            setText(hora.toString());
                            setStyle("-fx-font-weight: bold; -fx-alignment: center");
                        }
                    }
                };
            }
        });

        clienteColumn.setCellFactory(new Callback<TableColumn<Venda, String>, TableCell<Venda, String>>() {
            @Override
            public TableCell<Venda, String> call(TableColumn<Venda, String> vendaStringTableColumn) {
                return new TableCell<Venda, String>() {
                    @Override
                    protected void updateItem(String cliente, boolean empty) {
                        super.updateItem(cliente, empty);

                        if(empty){
                            setText(null);
                        }else {
                            setText(cliente);
                            setStyle("-fx-font-weight: bold; -fx-alignment: center");
                        }
                    }
                };
            }
        });

        metodoPagamentoColumn.setCellFactory(new Callback<TableColumn<Venda, String>, TableCell<Venda, String>>() {
            @Override
            public TableCell<Venda, String> call(TableColumn<Venda, String> vendaStringTableColumn) {
                return new TableCell<Venda, String>() {
                    @Override
                    protected void updateItem(String metodoPagamento, boolean empty) {
                        super.updateItem(metodoPagamento, empty);

                        if(empty){
                            setText(null);
                        }else {
                            setText(metodoPagamento);
                            setStyle("-fx-font-weight: bold; -fx-alignment: center");
                        }
                    }
                };
            }
        });

        totalColumn.setCellFactory(new Callback<TableColumn<Venda, Double>, TableCell<Venda, Double>>() {
            @Override
            public TableCell<Venda, Double> call(TableColumn<Venda, Double> vendaDoubleTableColumn) {
                return new TableCell<Venda, Double>() {
                    @Override
                    protected void updateItem(Double valorTotal, boolean empty) {
                        super.updateItem(valorTotal, empty);

                        if(empty){
                            setText(null);
                        }else {
                            setText("R$ " + String.format("%.2f", valorTotal).replace(".", ","));
                            setStyle("-fx-font-weight: bold; -fx-alignment: center");
                        }
                    }
                };
            }
        });

        pedidoColumn.setCellValueFactory(
                new PropertyValueFactory<Venda, Integer>("codigoDaVenda")
        );
        dataColumn.setCellValueFactory(
                new PropertyValueFactory<Venda, Date>("data")
        );
        horaColumn.setCellValueFactory(
                new PropertyValueFactory<Venda, Time>("hora")
        );
        clienteColumn.setCellValueFactory(
                new PropertyValueFactory<Venda, String>("cliente")
        );
        metodoPagamentoColumn.setCellValueFactory(
                new PropertyValueFactory<Venda, String>("metodoPagamento")
        );
        totalColumn.setCellValueFactory(
                new PropertyValueFactory<Venda, Double>("valorTotal")
        );
        vendasTableView.setItems(FXCollections.observableList(vendas));

        btnReembolso.setOnAction(actionEvent -> {
            Integer codigoDaVenda = !codigoDaVendaTextField.getText().isEmpty() ? Integer.valueOf(codigoDaVendaTextField.getText()) : null;

            Optional<Venda> ven = vendas.stream().filter(venda -> venda.getCodigoDaVenda().equals(codigoDaVenda)).findFirst();

            if(!ven.isPresent()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Solicitação de Reembolso");
                alert.setContentText("Pedido inexistente!");
                alert.showAndWait();
            } else {
                Double valorReembolso = !valorReembolsoTextField.getText().isEmpty() ? Double.valueOf(valorReembolsoTextField.getText()) : 0;

                if(valorReembolso <= 0 || valorReembolso > ven.get().getValorTotal()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Solicitação de Reembolso");
                    alert.setContentText("Valor do reembolso é inválido!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Solicitação de Reembolso");
                    alert.setContentText("Tem certeza que gostaria de reembolsar?");
                    Optional<ButtonType> resposta = alert.showAndWait();

                    if(resposta.isPresent() && resposta.get() == ButtonType.OK) {
                        SistemaVendaRestaurante sistemaVendaRestaurante = new SistemaVendaRestaurante();
                        sistemaVendaRestaurante.registrarReembolso(ven.get(), valorReembolso, adm);

                        Alert alertSucesso = new Alert(Alert.AlertType.INFORMATION);
                        alertSucesso.setTitle("Pedido Reembolso");
                        alertSucesso.setContentText("Reembolso realizada com sucesso!");
                        alertSucesso.showAndWait();

                        resetVendaReembolso(vendas);
                    } else {
                        System.out.println("Cancelar reembolso");
                    }
                }
            }
        });
    }

    private void resetVendaReembolso(List<Venda> vendas) {
        atualizarListagemVendas(vendas);
        vendasTableView.setItems(FXCollections.observableList(vendas));
        codigoDaVendaTextField.setText("");
        valorReembolsoTextField.setText("");
    }

    private static void atualizarListagemVendas(List<Venda> vendas) {
        List<Venda> vendasReembolsadas = new ArrayList<>();
        PedidoReembolsoDAO pedidoReembolsoDAO = new PedidoReembolsoDAO();
        for(Venda venda : vendas) {
            if(pedidoReembolsoDAO.existeReembolso(venda.getCodigoDaVenda()) == 1) {
                vendasReembolsadas.add(venda);
            }
        }
        vendas.removeAll(vendasReembolsadas);
    }
}
