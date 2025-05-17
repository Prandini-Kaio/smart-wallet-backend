package com.prandini.smartwallet.cartao.converter;

import com.prandini.smartwallet.cartao.domain.Cartao;
import com.prandini.smartwallet.cartao.model.CartaoOutput;
import org.springframework.stereotype.Component;

/**
 * @author kaiooliveira
 * created 15/05/2025
 */

@Component
public class CartaoConverter {

    public CartaoOutput toCartaoOutput(Cartao cartao) {
        return CartaoOutput.builder()
                .id(cartao.getId())
                .bancoId(cartao.getBanco().getId())
                .usuarioId(cartao.getUsuario().getId())
                .nome(cartao.getNome())
                .dataVencimento(cartao.getDataVencimento())
                .dataFechamento(cartao.getDataFechamento())
                .ativo(cartao.isAtivo())
                .build();
    }
}
