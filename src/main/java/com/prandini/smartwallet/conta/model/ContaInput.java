package com.prandini.smartwallet.conta.model;

/*
 * @author prandini
 * created 4/5/24
 */

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ContaInput {

    private Long id;

    @Schema(example = "ITAU")
    private String banco;

    @Schema(example = "CORRENTE")
    private String nome;

    private TipoConta tipoConta;

    @Schema(example = "2024-04-05")
    private LocalDate dtVencimento;
}
