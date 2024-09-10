package com.prandini.smartwallet.transacao.model;

/*
 * @author prandini
 * created 8/26/24
 */

import com.prandini.smartwallet.lancamento.domain.CategoriaLancamentoEnum;
import com.prandini.smartwallet.lancamento.domain.TipoLancamentoEnum;
import com.prandini.smartwallet.lancamento.domain.TipoPagamentoEnum;
import com.prandini.smartwallet.transacao.domain.StatusTransacaoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class TransacaoFilter {

    private Long id;

    private Long idLancamento;

    private CategoriaLancamentoEnum categoria;

    private TipoLancamentoEnum tipo;

    private TipoPagamentoEnum pagamento;

    private StatusTransacaoEnum status;

    private String conta;

    private LocalDateTime dtInicio;

    private LocalDateTime dtFim;
}
