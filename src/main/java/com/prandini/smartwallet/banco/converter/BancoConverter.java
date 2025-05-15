package com.prandini.smartwallet.banco.converter;

import com.prandini.smartwallet.banco.domain.Banco;
import com.prandini.smartwallet.banco.model.BancoOutput;
import com.prandini.smartwallet.cartao.converter.CartaoConverter;
import com.prandini.smartwallet.cartao.domain.Cartao;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @author kaiooliveira
 * created 15/05/2025
 */

@Component
public class BancoConverter {

    public BancoOutput toOutput(Banco banco) {
        return BancoOutput.builder()
                .id(banco.getId())
                .nome(banco.getNome())
                .cartoes(banco.getCartoes().stream().map(Cartao::getId).toList())
                .build();
    }
}
