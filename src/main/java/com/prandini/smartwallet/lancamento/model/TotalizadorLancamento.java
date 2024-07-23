package com.prandini.smartwallet.lancamento.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author kaiooliveira
 * created 22/07/2024
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalizadorLancamento {

    private BigDecimal total;

    private BigDecimal totalDebito;

    private BigDecimal totalCredito;
}
