package com.prandini.smartwallet.lancamento.domain;

public enum TipoPagamentoEnum {
    DEBITO("Débito"),

    CREDITO("Crédito");

    private String descricao;

    TipoPagamentoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
