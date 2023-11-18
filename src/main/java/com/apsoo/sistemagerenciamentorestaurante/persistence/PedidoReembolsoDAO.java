package com.apsoo.sistemagerenciamentorestaurante.persistence;

import java.sql.*;
import java.util.List;

import com.apsoo.sistemagerenciamentorestaurante.model.PedidoReembolso;

import javafx.scene.control.Alert;

public class PedidoReembolsoDAO implements DAO<PedidoReembolso, Integer> {

    @Override
    public PedidoReembolso inserir(PedidoReembolso pedidoReembolso) {
        Connection conexao = null;

        try {
            conexao = Conexao.abreConexao();

            String codeSQL = "INSERT INTO pedidos_reembolso (valor_reembolso, data, hora, administrador, venda_id) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conexao.prepareStatement(codeSQL);

            pstmt.setDouble(1, pedidoReembolso.getValorReembolso());
            pstmt.setDate(2, pedidoReembolso.getData());
            pstmt.setTime(3, pedidoReembolso.getHora());
            pstmt.setString(4, pedidoReembolso.getAdministrador());
            pstmt.setInt(5, pedidoReembolso.getVenda().getCodigoDaVenda());
            pstmt.execute();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pedido de Reembolso");
            alert.setContentText("Não foi possível registrar o pedido de reembolso!");
            alert.showAndWait();

            System.out.println(e);

            return null;
        } finally {
            Conexao.fechaConexao(conexao);
        }

        return pedidoReembolso;
    }

    @Override
    public void atualizar(PedidoReembolso objeto) {
        
    }    

    @Override
    public PedidoReembolso recuperar(Integer objetoID) {
        return null;
    }

    @Override
    public List<PedidoReembolso> recuperarTodos() {

        return null;
    }

    @Override
    public void remover(Integer objetoID) {
        
    }
    
}
