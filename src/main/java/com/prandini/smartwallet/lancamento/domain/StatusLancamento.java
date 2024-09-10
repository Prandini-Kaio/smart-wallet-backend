package com.prandini.smartwallet.lancamento.domain;

/**
 * @author kaiooliveira
 * created 06/08/2024
 */
public enum StatusLancamento {

    EM_ABERTO("Em aberto"),

    QUITADO("Quitado"),

    VENCIDO("Vencido"),

    CANCELADO("Cancelado");

    private final String descricao;

    StatusLancamento(String descricao){
        this.descricao = descricao;
    }
}
