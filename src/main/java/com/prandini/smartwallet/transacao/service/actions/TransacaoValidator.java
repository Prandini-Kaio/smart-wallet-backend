package com.prandini.smartwallet.transacao.service.actions;

/*
 * @author prandini
 * created 4/29/24
 */

import com.prandini.smartwallet.common.exception.BusinessException;
import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.domain.TransacaoStatusEnum;
import com.prandini.smartwallet.transacao.exceptions.TransacaoExceptionMessages;
import com.prandini.smartwallet.transacao.repository.TransacaoRepository;
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

        List<Transacao> transacoes = getter.findByIdLancamento(transacao.getLancamento().getId());

        boolean hasTransacaoAberta = transacoes.stream()
                .filter(t -> transacao.getId() != t.getId())
                .filter(t -> t.getStatus().equals(TransacaoStatusEnum.PENDENTE))
                .anyMatch(t -> t.getDtVencimento().isBefore(transacao.getDtVencimento()));

        if(hasTransacaoAberta)
            throw new BusinessException(TransacaoExceptionMessages.sequenciaInvalida());
    }

    private void validarStatus(Transacao transacao){
        if(transacao.getStatus().equals(TransacaoStatusEnum.CANCELADO))
            throw new BusinessException(TransacaoExceptionMessages.pagamentoIncorreto());

        if(transacao.getStatus().equals(TransacaoStatusEnum.PAGO))
            throw new BusinessException(TransacaoExceptionMessages.pagamentoJaEfetuado());
    }
}
