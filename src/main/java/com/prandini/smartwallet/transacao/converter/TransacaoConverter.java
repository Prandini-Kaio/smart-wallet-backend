package com.prandini.smartwallet.transacao.converter;



import com.prandini.smartwallet.common.utils.DateUtils;
import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.domain.dto.TransacaoOutput;

import java.util.List;

/*
 * @author prandini
 * created 4/16/24
 */
public class TransacaoConverter {

    public static List<TransacaoOutput> toListOutputs(List<Transacao> transacoes){
        return transacoes.stream().map(TransacaoConverter::toOutput).toList();
    }

    public static TransacaoOutput toOutput(Transacao transacao){

        String dtVencimento = DateUtils.toBrazilianDateString(transacao.getDtVencimento().toLocalDate());
        String dtPagamento = transacao.getDtPagamento() != null
                ? DateUtils.toBrazilianDateString(transacao.getDtPagamento().toLocalDate())
                : null;

        return TransacaoOutput.builder()
                .id(transacao.getId())
                .status(transacao.getStatus())
                .valor(transacao.getValor())
                .dtVencimento(dtVencimento)
                .dtPagamento(dtPagamento)
                .descricao(transacao.getDescricao())
                .build();
    }
}
