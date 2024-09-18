package com.prandini.smartwallet.conta.model;

/*
 * @author prandini
 * created 4/5/24
 */

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class ContaOutput {

    private Long id;

    private String banco;

    private String nome;

    private String dtVencimento;

    private Integer diaVencimento;

    private TipoConta tipoConta;

    private BigDecimal saldoParcial;
}
