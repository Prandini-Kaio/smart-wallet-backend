package com.prandini.smartwallet.assinatura.model;

/*
 * @author prandini
 * created 9/4/24
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class AssinaturaOutput {

    private Long id;

    private String conta;

    private BigDecimal valor;

    private LocalDate dtInicio;

    private LocalDate dtFim;

    private boolean ativa;

    private String descricao;
}
