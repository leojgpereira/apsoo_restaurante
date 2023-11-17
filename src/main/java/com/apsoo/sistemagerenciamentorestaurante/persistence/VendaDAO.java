package com.apsoo.sistemagerenciamentorestaurante.persistence;

import com.apsoo.sistemagerenciamentorestaurante.model.Venda;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO implements DAO<Venda, Integer> {
    @Override
    public Venda inserir(Venda venda) {
        Connection conexao = null;

        try {
            conexao = Conexao.abreConexao();

            String codeSQL = "INSERT INTO vendas (codigo_da_venda, valor_total, data, hora, metodo_pagamento, funcionario) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conexao.prepareStatement(codeSQL);

            pstmt.setInt(1, venda.getCodigoDaVenda());
            pstmt.setDouble(2, venda.getValorTotal());
            pstmt.setDate(3, venda.getData());
            pstmt.setTime(4, venda.getHora());
            pstmt.setString(5, venda.getMetodoPagamento());
            pstmt.setString(6, venda.getFuncionario());
            pstmt.execute();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Venda Balcão");
            alert.setContentText("Não foi possível registrar a venda!");
            alert.showAndWait();

            System.out.println(e);

            return null;
        } finally {
            Conexao.fechaConexao(conexao);
        }

        return venda;
    }

    @Override
    public void atualizar(Venda objeto) {

    }

    @Override
    public Venda recuperar(Integer objetoID) {
        return null;
    }

    @Override
    public List<Venda> recuperarTodos() {
        Connection conexao = null;
        List<Venda> vendas = new ArrayList<>();

        try {
            conexao = Conexao.abreConexao();
            Statement stmt = conexao.createStatement();
            ResultSet resultado = stmt.executeQuery("SELECT * FROM produtos");

            while(resultado.next()) {
                Venda venda = new Venda(
                        resultado.getInt("codigo_da_venda"),
                        resultado.getDouble("valor_total"),
                        resultado.getDate("data"),
                        resultado.getTime("hora"),
                        resultado.getString("metodo_pagamento"),
                        resultado.getString("funcionario")
                );

                vendas.add(venda);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Conexao.fechaConexao(conexao);
        }

        return vendas;
    }

    @Override
    public void remover(Integer objetoID) {

    }

    public Integer getNextId() {
        Connection conexao = null;
        Integer nextId = null;

        try {
            conexao = Conexao.abreConexao();
            Statement stmt = conexao.createStatement();
            ResultSet resultado = stmt.executeQuery("select * from vendas order by codigo_da_venda desc limit 1;");

            resultado.next();
            nextId = resultado.getInt("codigo_da_venda");
        } catch (SQLException e) {
            System.out.println(e);

            return null;
        } finally {
            Conexao.fechaConexao(conexao);
        }

        return nextId;
    }
}
