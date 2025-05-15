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

    public Long bancoId;

    public Long usuarioId;

    @NotNull(message = "O campo Data Vencimento é obrigatório.")
    public LocalDate dataVencimento;

    @NotNull(message = "O campo Data Fechamento é obrigatório.")
    public LocalDate dataFechamento;

    public boolean ativo = true;
}
