package com.prandini.smartwallet.assinatura.converter;

import com.prandini.smartwallet.assinatura.domain.Assinatura;
import com.prandini.smartwallet.assinatura.model.AssinaturaOutput;
import com.prandini.smartwallet.conta.converter.ContaConverter;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class AssinaturaConverter {

    @Resource
    private ContaConverter contaConverter;

    public AssinaturaOutput toOutput(Assinatura assinatura) {

        return AssinaturaOutput.builder()
                .id(assinatura.getId())
                .conta(contaConverter.toOutput(assinatura.getConta()))
                .valor(assinatura.getValor())
                .ativa(assinatura.isAtiva())
                .dtInicio(assinatura.getDtInicio())
                .dtFim(assinatura.getDtFim())
                .descricao(assinatura.getDescricao())
                .build();
    }
}
