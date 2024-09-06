package com.prandini.smartwallet.lancamento.model;

import com.prandini.smartwallet.lancamento.domain.CategoriaLancamentoEnum;
import com.prandini.smartwallet.lancamento.domain.StatusLancamento;
import com.prandini.smartwallet.lancamento.domain.TipoLancamentoEnum;
import com.prandini.smartwallet.lancamento.domain.TipoPagamentoEnum;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/*
 * @author prandini
 * created 4/29/24
 */

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class LancamentoFilter {

    private TipoLancamentoEnum tipo;

    private CategoriaLancamentoEnum categoria;

    private TipoPagamentoEnum tipoPagamento;

    private StatusLancamento status;

    private LocalDateTime dtInicio;

    private LocalDateTime dtFim;

    private String conta;
}
