package com.prandini.smartwallet.cartao.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author kaiooliveira
 * created 15/05/2025
 */

@Data
public class CartaoInput {

    private Long bancoId;

    private Long usuarioId;

    @NotNull(message = "O campo Nome é obrigatório.")
    private String nome;

    @NotNull(message = "O campo Data Vencimento é obrigatório.")
    private LocalDate dataVencimento;

    @NotNull(message = "O campo Data Fechamento é obrigatório.")
    private LocalDate dataFechamento;

    private boolean ativo = true;
}
