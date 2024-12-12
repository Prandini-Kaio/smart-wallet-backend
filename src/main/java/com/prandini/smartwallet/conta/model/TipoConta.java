package com.prandini.smartwallet.conta.model;

import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public String getDescricao() {
        return descricao;
    }

    public static List<TipoC> getTiposC(){
        List<TipoC> tipos = new ArrayList<>();

        for (TipoConta tipoConta : TipoConta.values()) {
            tipos.add(new TipoC(tipoConta.name(), tipoConta.getDescricao()));
        }

        return tipos;
    }
}
