package com.prandini.smartwallet.lancamento.model;

import com.prandini.smartwallet.lancamento.domain.CategoriaLancamentoEnum;
import com.prandini.smartwallet.lancamento.domain.TipoLancamentoEnum;
import com.prandini.smartwallet.lancamento.domain.TipoPagamentoEnum;
import com.prandini.smartwallet.transacao.domain.StatusTransacaoEnum;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author kaiooliveira
 * created 05/01/2025
 */

@Data
public class ResumoFinanceiroFilter {
    private Long id;

    private Long idLancamento;

    private List<CategoriaLancamentoEnum> categorias;

    private TipoLancamentoEnum tipo;

    private TipoPagamentoEnum pagamento;

    private List<StatusTransacaoEnum> status;

    private List<Long> contaIds;

    private LocalDate mes;
}
