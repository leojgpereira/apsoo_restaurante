package com.apsoo.sistemagerenciamentorestaurante.persistence;

import com.apsoo.sistemagerenciamentorestaurante.model.ItemVenda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ItemVendaDAO implements DAO<ItemVenda, Integer> {
    @Override
    public ItemVenda inserir(ItemVenda itemVenda) {
        Connection conexao = null;

        try {
            conexao = Conexao.abreConexao();

            String codeSQL = "INSERT INTO itens_venda (codigo_da_venda, produto_id, preco_unitario, quantidade, subtotal) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conexao.prepareStatement(codeSQL);

            pstmt.setInt(1, itemVenda.getVenda().getCodigoDaVenda());
            pstmt.setInt(2, itemVenda.getProduto().getCodigo());
            pstmt.setDouble(3, itemVenda.getProduto().getValor());
            pstmt.setInt(4, itemVenda.getQuantidade());
            pstmt.setDouble(5, itemVenda.getSubtotal());
            pstmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException();
        } finally {
            Conexao.fechaConexao(conexao);
        }

        return itemVenda;
    }

    @Override
    public void atualizar(ItemVenda objeto) {

    }

    @Override
    public ItemVenda recuperar(Integer objetoID) {
        return null;
    }

    @Override
    public List<ItemVenda> recuperarTodos() {
        return null;
    }

    @Override
    public void remover(Integer objetoID) {

    }
}
