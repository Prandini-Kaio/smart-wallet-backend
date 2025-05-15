package com.prandini.smartwallet.transacao.converter;

import com.prandini.smartwallet.transacao.domain.Parcela;
import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.model.ParcelaOutput;
import org.springframework.stereotype.Component;

/**
 * @author kaiooliveira
 * created 05/05/2025
 */

@Component
public class ParcelaConverter {

    public ParcelaOutput toOutput(Parcela parcela) {
        return ParcelaOutput.builder()
                .id(parcela.getId())
                .dataVencimento(parcela.getDataVencimento())
                .valor(parcela.getValor().toString())
                .cartaoId(parcela.getCartao().getId())
                .paga(parcela.getPaga())
                .numero(parcela.getNumero())
                .build();
    }
}
