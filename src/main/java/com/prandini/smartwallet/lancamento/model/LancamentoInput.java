package com.prandini.smartwallet.lancamento.model;

import com.prandini.smartwallet.lancamento.domain.CategoriaLancamentoEnum;
import com.prandini.smartwallet.lancamento.domain.StatusLancamento;
import com.prandini.smartwallet.lancamento.domain.TipoLancamentoEnum;
import com.prandini.smartwallet.lancamento.domain.TipoPagamentoEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/*
 * @author prandini
 * created 4/16/24
 */
@Getter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class LancamentoInput {

    @NotNull
    private TipoLancamentoEnum tipoLancamento;

    @NotNull
    private CategoriaLancamentoEnum categoriaLancamento;

    @NotNull
    private TipoPagamentoEnum tipoPagamento;

    private StatusLancamento status;

    @NotNull
    private BigDecimal valor;

    private LocalDateTime dtCriacao;

    private LocalDateTime dtAlteracaoStatus;

    @NotNull
    private int parcelas;

    @NotEmpty
    private String conta;

    private String descricao;
}
