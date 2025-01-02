package com.prandini.smartwallet.conta.model;

/*
 * @author prandini
 * created 9/22/24
 */

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContaFilter {

    private String nome;

    private String banco;

    private TipoConta tipoConta;

    private Integer diaVencimento;
}
