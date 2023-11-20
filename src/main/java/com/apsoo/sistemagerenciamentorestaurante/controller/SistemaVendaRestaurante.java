package com.apsoo.sistemagerenciamentorestaurante.controller;

import com.apsoo.sistemagerenciamentorestaurante.model.ItemVenda;
import com.apsoo.sistemagerenciamentorestaurante.model.PedidoReembolso;
import com.apsoo.sistemagerenciamentorestaurante.model.Venda;
import com.apsoo.sistemagerenciamentorestaurante.persistence.PedidoReembolsoDAO;
import com.apsoo.sistemagerenciamentorestaurante.persistence.VendaDAO;
import javafx.scene.control.ComboBox;

import java.util.List;

public class SistemaVendaRestaurante {
    public void registrarVenda(List<ItemVenda> listaProdutosSelecionados, Double valorTotalVenda, ComboBox<String> metodoPagamento, String func) {
        Venda venda = new Venda();
        venda.setCodigoDaVenda();
        venda.setValorTotal(valorTotalVenda);
        venda.setData();
        venda.setHora();
        venda.setMetodoPagamento(metodoPagamento.getValue());

        if(func != null) {
            venda.setFuncionario(func);
        }

        venda.setItensVenda(listaProdutosSelecionados);

        for(ItemVenda itemVenda : venda.getItensVenda()) {
            itemVenda.setVenda(venda);
        }

        VendaDAO vendaDAO = new VendaDAO();
        vendaDAO.inserir(venda);
    }

    public void registrarReembolso(Venda venda, Double valorReembolso, String adm) {
        PedidoReembolso reembolso = new PedidoReembolso();
        reembolso.setValorReembolso(valorReembolso);
        reembolso.setData();
        reembolso.setHora();
        reembolso.setVenda(venda);
        reembolso.setAdministrador(adm);

        PedidoReembolsoDAO pedidoReembolsoDAO = new PedidoReembolsoDAO();
        pedidoReembolsoDAO.inserir(reembolso);
    }
}
