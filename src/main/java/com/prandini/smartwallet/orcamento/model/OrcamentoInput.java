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

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class OrcamentoInput {

    private Long id;

    private BigDecimal valor;

    private CategoriaLancamentoEnum categoria;

    private Month mes;
}
