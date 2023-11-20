package com.apsoo.sistemagerenciamentorestaurante.persistence;

import com.apsoo.sistemagerenciamentorestaurante.model.PedidoReembolso;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.List;

public class PedidoReembolsoDAO implements DAO<PedidoReembolso, Integer> {

    @Override
    public PedidoReembolso inserir(PedidoReembolso reembolso) {
        Connection conexao = null;

        try {
            conexao = Conexao.abreConexao();

            String codeSQL = "INSERT INTO pedidos_reembolso (valor_reembolso, data, hora, administrador, venda_id) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conexao.prepareStatement(codeSQL);

            pstmt.setDouble(1, reembolso.getValorReembolso());
            pstmt.setDate(2, reembolso.getData());
            pstmt.setTime(3, reembolso.getHora());
            pstmt.setString(4, reembolso.getAdministrador());
            pstmt.setInt(5, reembolso.getVenda().getCodigoDaVenda());
            pstmt.execute();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pedido de Reembolso");
            alert.setContentText("Não foi possível registrar o pedido de reembolso!");
            alert.showAndWait();

            throw new RuntimeException(e);
        } finally {
            Conexao.fechaConexao(conexao);
        }

        return reembolso;
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

    public Integer existeReembolso(Integer vendaID) {
        Connection conexao = null;

        try {
            conexao = Conexao.abreConexao();

            String codeSQL = "SELECT COUNT(venda_id) AS reembolso FROM pedidos_reembolso WHERE venda_id = ?";

            PreparedStatement preparedStatement = conexao.prepareStatement(codeSQL);
            preparedStatement.setInt(1, vendaID);
            ResultSet resultado = preparedStatement.executeQuery();
            resultado.next();

            return resultado.getInt("reembolso");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Conexao.fechaConexao(conexao);
        }
    }
}
