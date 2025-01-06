package com.prandini.smartwallet.common.model;

/*
 * @author prandini
 * created 8/15/24
 */

import com.prandini.smartwallet.conta.model.ContaOutput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Month;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class ResumoFinanceiroOutput {

    private ContaOutput conta;

    private BigDecimal entradas;

    private BigDecimal saidas;

    private Month mes;
}
