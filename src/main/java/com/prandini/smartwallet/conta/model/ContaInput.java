package com.prandini.smartwallet.conta.model;

/*
 * @author prandini
 * created 4/5/24
 */

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ContaInput {

    private Long id;

    @Schema(example = "ITAU")
    private String banco;

    @Schema(example = "CORRENTE")
    private String nome;
}
