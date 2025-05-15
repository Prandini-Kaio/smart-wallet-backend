package com.prandini.smartwallet.transacao.model;

import com.prandini.smartwallet.transacao.domain.TipoTransacao;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author kaiooliveira
 * created 13/02/2025
 */

@Data
@AllArgsConstructor @NoArgsConstructor
public class TransacaoInput {

    @NotNull(message = "Campo usuario é obrigatório.")
    private Long usuarioId;

    private String descricao;

    @NotNull(message = "Campo tipo é obrigatório.")
    private TipoTransacao tipoTransacao;

    @NotNull(message = "Campo valor é obrigatório.")
    private BigDecimal valor;

    private LocalDate data;

    @NotNull(message = "Campo categoria é obrigatório.")
    private String categoria;

    @NotNull(message = "Campo forma de pagamento é obrigatório.")
    private String formaPagamento;

    private String observacao;

    private Integer numeroParcelas = 1;

    @NotNull(message = "O campo cartão é obrigatório.")
    private Long cartaoId;
}
