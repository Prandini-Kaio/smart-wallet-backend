package com.prandini.smartwallet.transacao.service.actions;

/*
 * @author prandini
 * created 4/29/24
 */

import com.prandini.smartwallet.common.exception.BusinessException;
import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.domain.StatusTransacaoEnum;
import com.prandini.smartwallet.transacao.exceptions.TransacaoExceptionMessages;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransacaoValidator {

    @Resource
    private TransacaoGetter getter;

    public void validarPagamento(Transacao transacao){
        this.validarSequencia(transacao);
        this.validarStatus(transacao);
    }

    private void validarSequencia(Transacao transacao) {

        List<Transacao> transacoes = getter.byIdLancamento(transacao.getLancamento().getId());

        boolean hasTransacaoAberta = transacoes.stream()
                .filter(t -> !transacao.getId().equals(t.getId()))
                .filter(t -> t.getStatus().equals(StatusTransacaoEnum.PENDENTE))
                .anyMatch(t -> t.getDtVencimento().isBefore(transacao.getDtVencimento()));

        if(hasTransacaoAberta)
            throw new BusinessException(TransacaoExceptionMessages.sequenciaInvalida());
    }

    private void validarStatus(Transacao transacao){
        if(transacao.getStatus().equals(StatusTransacaoEnum.CANCELADO))
            throw new BusinessException(TransacaoExceptionMessages.pagamentoIncorreto());

        if(transacao.getStatus().equals(StatusTransacaoEnum.PAGO))
            throw new BusinessException(TransacaoExceptionMessages.pagamentoJaEfetuado(transacao.getDescricao()));
    }
}
