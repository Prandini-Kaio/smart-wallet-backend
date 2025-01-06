package com.prandini.smartwallet.lancamento.model;

/*
 * @author prandini
 * created 4/5/24
 */
import com.prandini.smartwallet.conta.model.ContaOutput;
import com.prandini.smartwallet.lancamento.domain.CategoriaLancamentoEnum;
import com.prandini.smartwallet.lancamento.domain.StatusLancamento;
import com.prandini.smartwallet.lancamento.domain.TipoLancamentoEnum;
import com.prandini.smartwallet.lancamento.domain.TipoPagamentoEnum;
import com.prandini.smartwallet.transacao.domain.dto.TransacaoOutput;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@Data
public class LancamentoOutput {

    private Long id;

    private String tipoLancamento;

    private String categoriaLancamento;

    private String tipoPagamento;

    private BigDecimal valor;

    private ContaOutput conta;

    private String dtCriacao;

    private int parcelas;

    private String descricao;

    private List<TransacaoOutput> transacoes;

    private String status;

    private String icone;

}
