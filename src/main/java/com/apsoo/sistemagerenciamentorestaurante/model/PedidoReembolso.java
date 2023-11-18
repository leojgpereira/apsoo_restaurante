package com.apsoo.sistemagerenciamentorestaurante.model;

// import com.apsoo.sistemagerenciamentorestaurante.persistence.VendaDAO;

import java.sql.Time;
import java.sql.Date;

public class PedidoReembolso{
    private Double valorReembolso;
    private Date data;
    private Time hora;
    private String administrador;
    private Venda venda;

    public PedidoReembolso(Double valorReembolso, Date data, Time hora, String administrador, Venda venda) {
        this.valorReembolso = valorReembolso;
        this.data = data;
        this.hora = hora;
        this.administrador = administrador;
        this.venda = venda;
    }

    public PedidoReembolso() {
    }

    public Double getValorReembolso() {
        return valorReembolso;
    }

    public void setValorReembolso(Double valorReembolso) {
        this.valorReembolso = valorReembolso;
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

    public String getAdministrador() {
        return administrador;
    }

    public void setAdministrador(String administrador) {
        this.administrador = administrador;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }
}
