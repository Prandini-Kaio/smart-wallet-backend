package com.prandini.smartwallet.cartao.model;

import com.prandini.smartwallet.banco.domain.Banco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author kaiooliveira
 * created 15/05/2025
 */

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class CartaoOutput {

    private Long id;

    private Long bancoId;

    private String nome;

    private LocalDate dataVencimento;

    private LocalDate dataFechamento;

    private Long usuarioId;

    private boolean ativo;
}
