package com.prandini.smartwallet.orcamento.model;

import com.prandini.smartwallet.lancamento.domain.CategoriaLancamentoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Month;

/**
 * @author kaiooliveira
 * created 27/12/2024
 */

@Builder
@Data
@NoArgsConstructor @AllArgsConstructor
public class OrcamentoOutput {

    private Long id;

    private BigDecimal limite;

    private BigDecimal gastoAtual;

    private CategoriaLancamentoEnum categoria;

    private Month mes;

}
