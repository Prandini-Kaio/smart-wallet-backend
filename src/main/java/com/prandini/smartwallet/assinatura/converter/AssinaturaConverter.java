package com.prandini.smartwallet.assinatura.converter;

import com.prandini.smartwallet.assinatura.domain.Assinatura;
import com.prandini.smartwallet.assinatura.model.AssinaturaOutput;
import org.springframework.stereotype.Component;

@Component
public class AssinaturaConverter {

    public AssinaturaOutput toOutput(Assinatura assinatura) {

        return AssinaturaOutput.builder()
                .id(assinatura.getId())
                .conta(assinatura.getConta().getNome())
                .valor(assinatura.getValor())
                .dtInicio(assinatura.getDtInicio())
                .dtFim(assinatura.getDtFim())
                .descricao(assinatura.getDescricao())
                .lancamento(assinatura.isLancamento())
                .ativa(assinatura.isAtiva())
                .build();
    }
}
