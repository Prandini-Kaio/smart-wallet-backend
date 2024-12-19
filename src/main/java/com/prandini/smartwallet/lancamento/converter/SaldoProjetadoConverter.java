package com.prandini.smartwallet.lancamento.converter;

/*
 * @author prandini
 * created 12/18/24
 */

import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.lancamento.model.SaldoProjetadoOutput;
import com.prandini.smartwallet.transacao.domain.Transacao;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class SaldoProjetadoConverter {

    public SaldoProjetadoOutput toOutputLancamento(List<Lancamento> lancamentos, LocalDateTime data){

        BigDecimal entradas = BigDecimal.ZERO;
        BigDecimal saidas = BigDecimal.ZERO;
        BigDecimal saldo = BigDecimal.ZERO;

        for (Lancamento lancamento : lancamentos){
            if(lancamento.isEntrada()){
                entradas = entradas.add(lancamento.getTransacoes()
                        .stream()
                        .map(Transacao::getValor)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                );
            }else{
                saidas = saidas.add(lancamento.getTransacoes()
                        .stream()
                        .filter(Transacao::isProjetavel)
                        .filter(t -> t.getDtVencimento().compareTo(data) == 0)
                        .map(Transacao::getValor)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                );
            }
        }

        saldo = saldo.add(entradas.add(saidas.negate()));

        return SaldoProjetadoOutput.builder()
                .mes(data.getMonth())
                .entradas(entradas)
                .saidas(saidas)
                .saldo(saldo)
                .build();
    }

    public SaldoProjetadoOutput toOutputTransacao(List<Transacao> transacoes, LocalDateTime data){

        BigDecimal entradas = BigDecimal.ZERO;
        BigDecimal saidas = BigDecimal.ZERO;
        BigDecimal saldo = BigDecimal.ZERO;

        for (Transacao transacao : transacoes){
            if(transacao.getLancamento().isEntrada()){
                entradas = entradas.add(transacao.getValor());
            }else{
                saidas = saidas.add(transacao.getValor());
            }
        }

        saldo = saldo.add(entradas.add(saidas.negate()));

        return SaldoProjetadoOutput.builder()
                .mes(data.getMonth())
                .entradas(entradas)
                .saidas(saidas)
                .saldo(saldo)
                .build();
    }
}
