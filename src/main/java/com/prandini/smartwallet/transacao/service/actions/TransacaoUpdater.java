package com.prandini.smartwallet.transacao.service.actions;

/*
 * @author prandini
 * created 4/29/24
 */

import com.prandini.smartwallet.lancamento.service.actions.LancamentoUpdater;
import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.domain.StatusTransacaoEnum;
import com.prandini.smartwallet.transacao.repository.TransacaoRepository;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@CommonsLog
public class TransacaoUpdater {

    @Resource
    private TransacaoGetter getter;

    @Resource
    private TransacaoRepository repository;

    @Resource
    private TransacaoValidator validator;

    @Resource
    private LancamentoUpdater lancamentoUpdater;

    public Transacao pagar(Long id) {
        log.info(String.format("Pagando transação %s.", id));

        Transacao transacao = getter.byId(id);

        validator.validarPagamento(transacao);

        transacao.setStatus(StatusTransacaoEnum.PAGO);
        transacao.setDtPagamento(LocalDateTime.now());

        if(transacao.getProxima() == null)
            lancamentoUpdater.quitarLancamento(transacao.getLancamento().getId());

        return repository.save(transacao);
    }
}
