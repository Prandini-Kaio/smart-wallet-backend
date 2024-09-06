package com.prandini.smartwallet.assinatura.model;

/*
 * @author prandini
 * created 9/4/24
 */

import com.prandini.smartwallet.conta.domain.Conta;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AssinaturaInput {

    private Long id;

    @NotNull
    private String conta;

    @NotNull
    private BigDecimal valor;

    private LocalDate dtInicio;

    private LocalDate dtFim;

    private boolean ativa = true;

    @NotNull
    private String descricao;
}
