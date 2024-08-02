package com.prandini.smartwallet.conta.model;

import jakarta.persistence.Enumerated;

/**
 * @author kaiooliveira
 * created 02/08/2024
 */

public enum TipoConta {

    ECONOMIA("Economia"),

    INVESTIMENTO("Investimento"),

    CORRENTE_POUPANCA("Corrente/Poupança");

    private String descricao;

    TipoConta(String descricao){
        this.descricao = descricao;
    }
}
