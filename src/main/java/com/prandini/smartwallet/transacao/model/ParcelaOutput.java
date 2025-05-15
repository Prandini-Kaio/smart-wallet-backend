package com.prandini.smartwallet.transacao.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author kaiooliveira
 * created 05/05/2025
 */

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ParcelaOutput {

    private Long id;

    private Integer numero;

    private String valor;

    private LocalDate dataVencimento;

    private Boolean paga;

    private Long cartaoId;
}
