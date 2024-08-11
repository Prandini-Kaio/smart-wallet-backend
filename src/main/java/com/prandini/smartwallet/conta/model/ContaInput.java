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

    @Pattern(regexp = "^(0[1-9]|[12][0-9]|30)/(0[1-9]|1[0-2])|31/(0[13578]|1[02])$\n")
    @Schema(example = "01/12")
    private String dtVencimento;
}
