package com.prandini.smartwallet.conta.converter;



import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.conta.model.ContaOutput;
import com.prandini.smartwallet.lancamento.domain.Lancamento;

import java.math.BigDecimal;

/*
 * @author prandini
 * created 4/5/24
 */
public class ContaConverter {

    public static ContaOutput toOutput(Conta conta){

        BigDecimal totalLancamentos = BigDecimal.ZERO;
        if(conta.getLancamentos() != null)
            totalLancamentos = conta.getLancamentos().stream()
                .map(Lancamento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return ContaOutput.builder()
                .id(conta.getId())
                .banco(conta.getBanco())
                .nome(conta.getNome())
                .saldoParcial(conta.getSaldoParcial())
                .totalLancamentos(totalLancamentos)
                .build();
    }
}
