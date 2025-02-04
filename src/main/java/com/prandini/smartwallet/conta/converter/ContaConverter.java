package com.prandini.smartwallet.conta.converter;



import com.prandini.smartwallet.common.utils.DateUtils;
import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.conta.model.ContaInput;
import com.prandini.smartwallet.conta.model.ContaOutput;
import com.prandini.smartwallet.conta.service.actions.ContaGetter;
import com.prandini.smartwallet.lancamento.domain.Lancamento;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

/*
 * @author prandini
 * created 4/5/24
 */

@Component
public class ContaConverter {

    @Resource
    private ContaGetter getter;

    public ContaOutput toOutput(Conta conta){

        LocalDate now = LocalDate.now();
        YearMonth nowYM = YearMonth.now();

        BigDecimal saldoParcial = getter.getSaldoParcialConta(conta);

        LocalDate dtFechamento = conta.getDiaVencimento() < conta.getDiaFechamento() ? conta.getDiaFechamento(nowYM.minusMonths(1)) : conta.getDiaFechamento(nowYM);

        return ContaOutput.builder()
                .id(conta.getId())
                .banco(conta.getBanco())
                .nome(conta.getNome())
                .saldoParcial(saldoParcial)
                .dtVencimento(DateUtils.toBrazilianDayMonthString(conta.getDiaVencimento(nowYM)))
                .dtFechamento(DateUtils.toBrazilianDayMonthString(dtFechamento))
                .tipoConta(conta.getTipoConta())
                .color(conta.getColor())
                .build();
    }

    public ContaInput toInput(Conta conta){
        return ContaInput.builder()
                .banco(conta.getBanco())
                .nome(conta.getNome())
                .tipoConta(conta.getTipoConta())
                .diaVencimento(String.valueOf(conta.getDiaVencimento()))
                .diaFechamento(String.valueOf(conta.getDiaFechamento()))
                .color(conta.getColor())
                .build();
    }
}
