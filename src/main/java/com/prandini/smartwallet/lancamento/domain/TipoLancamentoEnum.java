package com.prandini.smartwallet.lancamento.domain;

public enum TipoLancamentoEnum {
    ENTRADA("Entrada"),

    SAIDA("Saída"),

    TRANSFERENCIA("Transferência");

    private final String descricao;

    TipoLancamentoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isSaida() {
        return this.equals(SAIDA);
    }

    public boolean isEntrada() {
        return this.equals(ENTRADA);
    }

    public boolean isTransferencia() {
        return this.equals(TRANSFERENCIA);
    }
}
