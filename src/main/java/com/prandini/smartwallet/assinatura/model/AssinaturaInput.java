package com.prandini.smartwallet.assinatura.model;

/*
 * @author prandini
 * created 9/4/24
 */

import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.lancamento.domain.CategoriaLancamentoEnum;
import com.prandini.smartwallet.lancamento.domain.TipoLancamentoEnum;
import com.prandini.smartwallet.lancamento.domain.TipoPagamentoEnum;
import com.prandini.smartwallet.lancamento.model.LancamentoInput;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AssinaturaInput {

    private Long id;

    private Long contaId;

    private CategoriaLancamentoEnum categoria;

    private TipoLancamentoEnum tipo;

    private TipoPagamentoEnum pagamento;

    private BigDecimal valor;

    private String descricao;

    private LocalDate dtInicio;

    private LocalDate dtFim;

    private boolean ativa = true;
}
