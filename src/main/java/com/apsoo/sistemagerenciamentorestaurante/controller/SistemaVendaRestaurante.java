package com.apsoo.sistemagerenciamentorestaurante.controller;

import com.apsoo.sistemagerenciamentorestaurante.model.ItemVenda;
import com.apsoo.sistemagerenciamentorestaurante.model.Produto;
import com.apsoo.sistemagerenciamentorestaurante.model.Venda;
import com.apsoo.sistemagerenciamentorestaurante.persistence.ProdutoDAO;
import com.apsoo.sistemagerenciamentorestaurante.persistence.VendaDAO;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.Duration;
import org.decimal4j.util.DoubleRounder;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SistemaVendaRestaurante implements Initializable {
    @FXML
    private Text timeText;
    @FXML
    private Button btnAlimentacao;
    @FXML
    private Button btnBebidas;
    @FXML
    private Button btnDoces;
    @FXML
    private ScrollPane produtosScrollPane;
    @FXML
    private ListView<Produto> produtosListView;
    @FXML
    private TableView itensPedidoTableView;
    @FXML
    private TableColumn<ItemVenda, String> itemColumn;
    @FXML
    private TableColumn<ItemVenda, Double> valorUnitarioColumn;
    @FXML
    private TableColumn<ItemVenda, Integer> quantidadeColumn;
    @FXML
    private TableColumn<ItemVenda, Double> subtotalColumn;
    @FXML
    private Text totalVendaText;
    @FXML
    private ComboBox<String> metodosPagamentoComboBox;
    @FXML
    private Button finalizarVenda;

    private String categoriaProdutos = "TODOS";
    private List<ItemVenda> itemVendaList = new ArrayList<>();
    private Double total = 0.0;
    private String func = "LEANDRO";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /* Carrega o horário para o campo correspondente na interface gráfica */
        Timeline clock = new Timeline(
                    new KeyFrame(
                            Duration.ZERO, e -> timeText.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
                    )
                ),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

        /* Carrega lista de produtos cadastrados */
        ProdutoDAO produtoDAO = new ProdutoDAO();
        List<Produto> produtos = produtoDAO.recuperarTodos();
        
        /* Cria um UI personalizado para mostrar cada produto e carrega os produtos para o ListView */
        ObservableList<Produto> produtoObservableList = FXCollections.observableArrayList(produtos);
        criarUIProduto();
        produtosListView.setItems(produtoObservableList);
        
        /* Define o comportamento dos botões de escolha de categorias */
        escolherCategoriasUI(produtos);
        
        /* Configura a TableView para mostrar os itens adicionados a venda */
        itensPedidoTableView.setPlaceholder(new Label("Não há itens no pedido"));
        itemColumn.setCellValueFactory(
                new PropertyValueFactory<>("produto")
        );
        valorUnitarioColumn.setCellValueFactory(
                new PropertyValueFactory<>("precoUnitario")
        );
        quantidadeColumn.setCellValueFactory(
                new PropertyValueFactory<>("quantidade")
        );
        subtotalColumn.setCellValueFactory(
                new PropertyValueFactory<>("subtotal")
        );
        
        /* Inicializa o total da venda com R$0,00 */
        totalVendaText.setText("R$ " + String.format("%.2f", total).replace(".", ","));
        
        /* Carrega os métodos de pagamento aceitos */
        List<String> metodosPagamento = List.of("DINHEIRO", "DÉBITO", "CRÉDITO", "PIX", "VALE-REFEIÇÃO");
        ObservableList<String> metodosPagamentoObservableList = FXCollections.observableArrayList(metodosPagamento);
        metodosPagamentoComboBox.setItems(metodosPagamentoObservableList);
        metodosPagamentoComboBox.setPromptText("Selecione...");

        finalizarVenda.setOnAction(actionEvent -> {
            registrarVenda(itemVendaList, metodosPagamentoComboBox, total);
        });
    }

    private void registrarVenda(List<ItemVenda> listaProdutosSelecionados, ComboBox<String> metodoPagamento, Double valorTotalVenda) {
        Venda ven = criarVenda();

        if(ven != null) {
            ven.setCodigoDaVenda();
            ven.setValorTotal(valorTotalVenda);
            ven.setData(Date.valueOf(LocalDate.now()));
            ven.setHora(Time.valueOf(LocalTime.now()));
            ven.setMetodoPagamento(metodoPagamento.getValue());

            if(func != null) {
                ven.setFuncionario(func);
            }

            ven.setItensVenda(listaProdutosSelecionados);

            VendaDAO vendaDAO = new VendaDAO();
            vendaDAO.inserir(ven);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Venda Balcão");
            alert.setContentText("Venda realizada com sucesso!");
            alert.showAndWait();
        }
    }

    private Venda criarVenda() {
        return new Venda();
    }

    private void escolherCategoriasUI(List<Produto> produtos) {
        incluirCategoria(btnAlimentacao, btnBebidas, btnDoces, "ALIMENTO", produtos);
        incluirCategoria(btnBebidas, btnAlimentacao, btnDoces, "BEBIDA", produtos);
        incluirCategoria(btnDoces, btnAlimentacao, btnBebidas, "DOCE", produtos);
    }

    private void incluirCategoria(Button btnCategoriaPrincipal, Button btnSegundaCategoria, Button btnTerceiraCategoria, String categoria, List<Produto> produtos) {
        btnCategoriaPrincipal.setOnMouseClicked(mouseEvent -> {
            if(!categoriaProdutos.equals(categoria)) {
                List<Produto> produtosSegmentados = produtos.stream().filter(produto -> produto.getCategoria().equals(categoria)).collect(Collectors.toList());

                produtosListView.setItems(FXCollections.observableList(produtosSegmentados));

                categoriaProdutos = categoria;
                btnCategoriaPrincipal.setStyle("-fx-background-radius: 20; -fx-background-color: #000;");
                btnSegundaCategoria.setStyle("-fx-background-radius: 20; -fx-background-color: #343A40;");
                btnTerceiraCategoria.setStyle("-fx-background-radius: 20; -fx-background-color: #343A40;");
            } else {
                produtosListView.setItems(FXCollections.observableList(produtos));

                categoriaProdutos = "TODOS";
                btnCategoriaPrincipal.setStyle("-fx-background-radius: 20; -fx-background-color: #343A40;");
            }
        });
    }

    private void criarUIProduto() {
        produtosListView.setCellFactory(new Callback<ListView<Produto>, ListCell<Produto>>() {
            @Override
            public ListCell<Produto> call(ListView<Produto> produtoListView) {
                return new ListCell<Produto>() {
                    @Override
                    protected void updateItem(Produto produto, boolean empty) {
                        super.updateItem(produto, empty);

                        if (produto == null || empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            HBox root = new HBox(10);
                            root.setAlignment(Pos.CENTER_LEFT);
                            root.setPadding(new Insets(5, 10, 5, 10));

                            root.getChildren().add(new Label(produto.getDescricao().toUpperCase()));

                            Region region = new Region();
                            HBox.setHgrow(region, Priority.ALWAYS);
                            root.getChildren().add(region);

                            Label valorProduto = new Label("R$ " + String.format("%5.2f", produto.getValor()).replace(".", ","));
                            root.getChildren().add(valorProduto);

                            Optional<ItemVenda> itemPedido = itemVendaList.stream().filter(itemVenda -> itemVenda.getProduto().equals(produto)).findFirst();

                            TextField quantidadeTextField = new TextField();

                            if(itemPedido.isPresent()) {
                                quantidadeTextField.setText(itemPedido.get().getQuantidade().toString());
                            } else {
                                quantidadeTextField.setText("0");
                            }

                            quantidadeTextField.setAlignment(Pos.CENTER);

                            Button btnRemoverItem = new Button("-");
                            btnRemoverItem.setOnAction(event -> {
                                Integer qtdeItem = Integer.valueOf(quantidadeTextField.getText());

                                if(qtdeItem > 0) {
                                    quantidadeTextField.setText(String.valueOf(qtdeItem - 1));

                                    Optional<ItemVenda> item = itemVendaList.stream().filter(itemVenda -> itemVenda.getProduto().equals(produto)).findFirst();

                                    if(item.isPresent()) {
                                        ItemVenda itemVenda = item.get();

                                        itemVenda.setQuantidade(itemVenda.getQuantidade() - 1);
                                        itemVenda.setSubtotal(itemVenda.getPrecoUnitario() * itemVenda.getQuantidade());

                                        if(itemVenda.getQuantidade() == 0) {
                                            itemVendaList.remove(itemVenda);
                                        }
                                    }

                                    atualizarPedido();
                                }
                            });

                            Button btnAdicionarItem = new Button("+");
                            btnAdicionarItem.setOnAction(event -> {
                                Integer qtdeItem = Integer.valueOf(quantidadeTextField.getText());

                                if(qtdeItem + 1 <= produto.getQuantidade()) {
                                    quantidadeTextField.setText(String.valueOf(qtdeItem + 1));

                                    Optional<ItemVenda> item = itemVendaList.stream().filter(itemVenda -> itemVenda.getProduto().equals(produto)).findFirst();

                                    if(item.isPresent()) {
                                        ItemVenda itemVenda = item.get();

                                        itemVenda.setQuantidade(itemVenda.getQuantidade() + 1);
                                        itemVenda.setSubtotal(
                                                DoubleRounder.round((itemVenda.getPrecoUnitario() * itemVenda.getQuantidade()), 2)
                                        );
                                    } else {
                                        ItemVenda itemVenda = new ItemVenda(produto, 1);
                                        itemVendaList.add(itemVenda);
                                    }

                                    atualizarPedido();
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setTitle(produto.getDescricao());
                                    alert.setContentText("Item indisponível no momento!");
                                    alert.showAndWait();
                                }
                            });

                            root.getChildren().addAll(btnRemoverItem, quantidadeTextField, btnAdicionarItem);

                            setText(null);
                            setGraphic(root);
                        }
                    }
                };
            }
        });
    }

    private void atualizarPedido() {
        total = 0.0;

        for(ItemVenda itemVenda : itemVendaList) {
            total += itemVenda.getSubtotal();
        }

        ObservableList<ItemVenda> itemVendaObservableList = FXCollections.observableArrayList(itemVendaList);
        itensPedidoTableView.setItems(itemVendaObservableList);
        itensPedidoTableView.refresh();

        totalVendaText.setText("R$ " + String.format("%.2f", total).replace(".", ","));
    }
}
