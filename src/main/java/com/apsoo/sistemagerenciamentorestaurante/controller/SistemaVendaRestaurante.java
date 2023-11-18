package com.apsoo.sistemagerenciamentorestaurante.controller;

import com.apsoo.sistemagerenciamentorestaurante.model.ItemVenda;
import com.apsoo.sistemagerenciamentorestaurante.model.PedidoReembolso;
import com.apsoo.sistemagerenciamentorestaurante.model.Venda;
import com.apsoo.sistemagerenciamentorestaurante.persistence.PedidoReembolsoDAO;
import com.apsoo.sistemagerenciamentorestaurante.persistence.VendaDAO;
import javafx.scene.control.ComboBox;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class SistemaVendaRestaurante {
    public Venda registrarVenda(List<ItemVenda> listaProdutosSelecionados, ComboBox<String> metodoPagamento, Double valorTotalVenda, String func) {
        Venda venda = new Venda();
        venda.setCodigoDaVenda();
        venda.setValorTotal(valorTotalVenda);
        venda.setData(Date.valueOf(LocalDate.now()));
        venda.setHora(Time.valueOf(LocalTime.now()));
        venda.setMetodoPagamento(metodoPagamento.getValue());

        if(func != null) {
            venda.setFuncionario(func);
        }

        venda.setItensVenda(listaProdutosSelecionados);

        for(ItemVenda itemVenda : venda.getItensVenda()) {
            itemVenda.setVenda(venda);
        }

        if(metodoPagamento.getValue() != null) {
            VendaDAO vendaDAO = new VendaDAO();
            vendaDAO.inserir(venda);

            return venda;
        }

        return null;
    }

    public void registrarReembolso(Venda venda, Double valorReembolso, String adm) {
        PedidoReembolso pedidoReembolso = new PedidoReembolso();
        pedidoReembolso.setValorReembolso(valorReembolso);
        pedidoReembolso.setData(Date.valueOf(LocalDate.now()));
        pedidoReembolso.setHora(Time.valueOf(LocalTime.now()));
        pedidoReembolso.setAdministrador(adm);
        pedidoReembolso.setVenda(venda);

        PedidoReembolsoDAO pedidoReembolsoDAO = new PedidoReembolsoDAO();

        try {
            pedidoReembolsoDAO.inserir(pedidoReembolso);
            System.out.println("Reembolso registrado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao registrar reembolso: " + e.getMessage());
        }
    }
}
