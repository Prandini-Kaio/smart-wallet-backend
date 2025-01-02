package com.prandini.smartwallet.lancamento.model;

import com.prandini.smartwallet.lancamento.domain.CategoriaLancamentoEnum;
import com.prandini.smartwallet.lancamento.domain.TipoLancamentoEnum;
import com.prandini.smartwallet.lancamento.domain.TipoPagamentoEnum;
import com.prandini.smartwallet.transacao.domain.StatusTransacaoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author kaiooliveira
 * created 01/01/2025
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaldoProjetadoFilter {
    private List<CategoriaLancamentoEnum> categorias;

    private TipoLancamentoEnum tipo;

    private TipoPagamentoEnum pagamento;

    private List<StatusTransacaoEnum> status;

    private List<Long> contaIds;

    private LocalDateTime dtInicio;

    private LocalDateTime dtFim;
}
