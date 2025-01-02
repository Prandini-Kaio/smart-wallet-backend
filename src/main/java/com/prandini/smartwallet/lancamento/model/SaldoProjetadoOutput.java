package com.prandini.smartwallet.lancamento.model;

/*
 * @author prandini
 * created 12/18/24
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class SaldoProjetadoOutput {

    private String mes;

    private BigDecimal entradas;

    private BigDecimal saidas;

    private BigDecimal saldo;
}
