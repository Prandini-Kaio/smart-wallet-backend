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
                .bancoId(cartao.getBanco().getId())
                .dataVencimento(cartao.getDataVencimento())
                .dataFechamento(cartao.getDataFechamento())
                .ativo(cartao.isAtivo())
                .build();
    }
}
