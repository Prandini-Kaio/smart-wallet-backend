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

    public boolean isDebito() {
        return this.equals(DEBITO);
    }

    public boolean isCredito() {
        return this.equals(CREDITO);
    }
}
