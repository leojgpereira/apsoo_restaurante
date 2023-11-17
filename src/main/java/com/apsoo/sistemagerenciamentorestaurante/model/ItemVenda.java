package com.apsoo.sistemagerenciamentorestaurante.model;

public class ItemVenda {
    private Produto produto;
    private Double precoUnitario;
    private Integer quantidade;
    private Double subtotal;
    private Venda venda;

    public ItemVenda(Produto produto, Integer quantidade) {
        this.produto = produto;
        this.precoUnitario = produto.getValor();
        this.quantidade = quantidade;
        this.subtotal = precoUnitario * quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }
}
