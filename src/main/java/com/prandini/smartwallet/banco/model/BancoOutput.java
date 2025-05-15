package com.prandini.smartwallet.banco.model;

import com.prandini.smartwallet.cartao.model.CartaoOutput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author kaiooliveira
 * created 15/05/2025
 */

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class BancoOutput {

    private Long id;

    private String nome;

    private List<Long> cartoes;
}
