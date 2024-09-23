package com.prandini.smartwallet.conta.model;

/*
 * @author prandini
 * created 9/22/24
 */

import lombok.Data;

@Data
public class ContaFilter {

    private String nome;

    private TipoConta tipoConta;

    private Integer diaVencimento;
}
