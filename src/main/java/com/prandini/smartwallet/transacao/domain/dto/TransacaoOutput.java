package com.prandini.smartwallet.transacao.domain.dto;

/*
 * @author prandini
 * created 4/5/24
 */

import com.prandini.smartwallet.lancamento.model.LancamentoOutput;
import com.prandini.smartwallet.transacao.domain.StatusTransacaoEnum;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class TransacaoOutput {

    private Long id;

    private LancamentoOutput lancamento;

    private BigDecimal valor;

    private StatusTransacaoEnum status;

    private String dtVencimento;

    private String dtPagamento;

    private String descricao;
}
