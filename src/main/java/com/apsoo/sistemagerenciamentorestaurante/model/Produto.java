package com.apsoo.sistemagerenciamentorestaurante.model;

import java.time.LocalDate;

public class Produto {
    private Integer codigo;
    private String descricao;
    private String categoria;
    private Double valor;
    private LocalDate dataValidade;
    private Integer quantidade;

    public Produto(Integer codigo, String descricao, String categoria, Double valor, LocalDate dataValidade, Integer quantidade) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.valor = valor;
        this.dataValidade = dataValidade;
        this.quantidade = quantidade;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
