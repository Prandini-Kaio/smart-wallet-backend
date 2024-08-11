package com.prandini.smartwallet.conta.converter;



import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.conta.model.ContaOutput;
import com.prandini.smartwallet.conta.service.actions.ContaGetter;
import com.prandini.smartwallet.lancamento.domain.Lancamento;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/*
 * @author prandini
 * created 4/5/24
 */

@Component
public class ContaConverter {

    @Resource
    private ContaGetter getter;

    public ContaOutput toOutput(Conta conta){

        BigDecimal saldoParcial = getter.getSaldoParcialConta(conta.getId());

        return ContaOutput.builder()
                .id(conta.getId())
                .banco(conta.getBanco())
                .nome(conta.getNome())
                .saldoParcial(saldoParcial)
                .dtVencimento(conta.getDtVencimento())
                .tipoConta(conta.getTipoConta())
                .build();
    }
}
