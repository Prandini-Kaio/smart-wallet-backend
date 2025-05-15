package com.prandini.smartwallet.transacao.converter;


import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.model.TransacaoOutput;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/*
 * @author prandini
 * created 4/16/24
 */

@Component
public class TransacaoConverter {

    @Resource
    private ParcelaConverter parcelaConverter;

    public List<TransacaoOutput> toListOutputs(List<Transacao> transacoes){
        return transacoes.stream().map(this::toOutput).toList();
    }

    public TransacaoOutput toOutput(Transacao transacao){

        return TransacaoOutput.builder()
                .usuarioId(transacao.getUsuario().getId())
                .tipoTransacao(transacao.getTipo())
                .categoria(transacao.getCategoria())
                .data(transacao.getData() != null ? transacao.getData().toString() : "")
                .valor(transacao.getValor().toString())
                .observacao(transacao.getObservacao())
                .parcelas(transacao.getParcelas() != null ? transacao.getParcelas().stream().map(parcelaConverter::toOutput).collect(Collectors.toList()) : null)
                .numeroParcelas(transacao.getNumeroParcelas())
                .build();
    }
}
