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

    ASSETS(1, "Ativos"), // ENTRADAS

    LIABILITIES(2, "Passivos"), // SAIDAS

    ECONOMIA(3, "Economia"),

    INVESTIMENTO(4, "Investimento"),

    CORRENTE_POUPANCA(5, "Corrente/Poupança");

    private Integer id;

    private String descricao;

    TipoConta(Integer id, String descricao){
        this.id = id;
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
