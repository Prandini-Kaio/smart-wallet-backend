package com.prandini.smartwallet.common.model;

/*
 * @author prandini
 * created 8/15/24
 */

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ResumoFinanceiro {

    private String mesAnterior;
    private BigDecimal valorMesAnterior;


}
