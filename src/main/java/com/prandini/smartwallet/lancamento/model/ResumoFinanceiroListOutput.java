package com.prandini.smartwallet.lancamento.model;

import com.prandini.smartwallet.conta.model.ContaOutput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author kaiooliveira
 * created 05/01/2025
 */

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class ResumoFinanceiroListOutput {

    private ContaOutput conta;

    private List<BigDecimal> valores;
}
