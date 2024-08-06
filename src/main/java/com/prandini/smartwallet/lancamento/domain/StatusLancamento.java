package com.prandini.smartwallet.lancamento.domain;

/**
 * @author kaiooliveira
 * created 06/08/2024
 */
public enum StatusLancamento {

    QUITADO("Quitado");

    private final String descricao;

    StatusLancamento(String descricao){
        this.descricao = descricao;
    }
}
