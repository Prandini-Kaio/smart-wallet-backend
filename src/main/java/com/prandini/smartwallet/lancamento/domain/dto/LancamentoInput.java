package com.prandini.smartwallet.lancamento.domain.dto;

import com.prandini.smartwallet.lancamento.domain.CategoriaLancamentoEnum;
import com.prandini.smartwallet.lancamento.domain.TipoLancamentoEnum;
import com.prandini.smartwallet.lancamento.domain.TipoPagamentoEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

/*
 * @author prandini
 * created 4/16/24
 */
@Getter
public class LancamentoInput {

    @NotNull
    private TipoLancamentoEnum tipoLancamento;

    @NotNull
    private TipoPagamentoEnum tipoPagamento;

    @NotNull
    private CategoriaLancamentoEnum categoriaLancamento;

    @NotNull
    private BigDecimal valor;

    @NotNull
    private int parcelas;

    @NotEmpty
    private String conta;

    private String descricao;

    private String icone;
}
