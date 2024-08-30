package com.prandini.smartwallet.transacao.domain;

public enum StatusTransacaoEnum {

    PENDENTE("Pendente"),

    PAGO("Pago"),

    ATRASADO("Atrasado"),

    CANCELADO("Cancelado");

    private final String descricao;

    StatusTransacaoEnum(String descricao) {
        this.descricao = descricao;
    }

    public StatusTransacaoEnum fromDescricao(){
        return StatusTransacaoEnum.valueOf(this.descricao);
    }
}
