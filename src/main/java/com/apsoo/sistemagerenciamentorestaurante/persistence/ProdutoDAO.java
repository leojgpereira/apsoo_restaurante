package com.apsoo.sistemagerenciamentorestaurante.persistence;

import com.apsoo.sistemagerenciamentorestaurante.model.Produto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO implements DAO<Produto, Integer>{
    @Override
    public Produto inserir(Produto objeto) {
        return null;
    }

    @Override
    public void atualizar(Produto objeto) {

    }

    @Override
    public Produto recuperar(Integer objetoID) {
        return null;
    }

    @Override
    public List<Produto> recuperarTodos() {
        Connection conexao = null;
        List<Produto> produtos = new ArrayList<>();

        try {
            conexao = Conexao.abreConexao();
            Statement stmt = conexao.createStatement();
            ResultSet resultado = stmt.executeQuery("SELECT * FROM produtos");

            while(resultado.next()) {
                Produto produto = new Produto(
                        resultado.getInt("codigo"), resultado.getString("descricao"), resultado.getString("categoria"), resultado.getDouble("valor"), resultado.getDate("dataValidade").toLocalDate(),
                        resultado.getInt("quantidade")
                );

                produtos.add(produto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Conexao.fechaConexao(conexao);
        }

        return produtos;
    }

    @Override
    public void remover(Integer objetoID) {

    }
}
