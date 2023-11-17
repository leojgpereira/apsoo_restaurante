package com.apsoo.sistemagerenciamentorestaurante.model;

import com.apsoo.sistemagerenciamentorestaurante.persistence.VendaDAO;

import java.sql.Time;
import java.sql.Date;
import java.util.List;

public class Venda {
    private Integer codigoDaVenda;
    private Double valorTotal;
    private Date data;
    private Time hora;
    private String metodoPagamento;
    private String funcionario;
    private List<ItemVenda> itensVenda;

    public Venda(Integer codigoDaVenda, Double valorTotal, Date data, Time hora, String metodoPagamento, String funcionario) {
        this.codigoDaVenda = codigoDaVenda;
        this.valorTotal = valorTotal;
        this.data = data;
        this.hora = hora;
        this.metodoPagamento = metodoPagamento;
        this.funcionario = funcionario;
        this.itensVenda = itensVenda;
    }

    public Venda() {
    }

    public Integer getCodigoDaVenda() {
        return codigoDaVenda;
    }

    public void setCodigoDaVenda() {
        VendaDAO vendaDAO = new VendaDAO();
        this.codigoDaVenda = vendaDAO.getNextId() + 1;
        System.out.println(codigoDaVenda);
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public String getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(String funcionario) {
        this.funcionario = funcionario;
    }

    public List<ItemVenda> getItensVenda() {
        return itensVenda;
    }

    public void setItensVenda(List<ItemVenda> itensVenda) {
        this.itensVenda = itensVenda;
    }
}
