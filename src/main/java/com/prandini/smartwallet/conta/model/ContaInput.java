package com.prandini.smartwallet.conta.model;

/*
 * @author prandini
 * created 4/5/24
 */

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ContaInput {

    private Long id;

    @NotNull
    @Schema(example = "ITAU")
    private String banco;

    @NotNull
    @Schema(example = "CORRENTE")
    private String nome;

    @NotNull
    @Schema(example = "ECONOMIA")
    private TipoConta tipoConta;

    @NotNull
    @Schema(example = "12")
    private String diaVencimento;

    @Schema(example = "#FFFFF")
    private String color;
}
