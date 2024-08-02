package com.prandini.smartwallet.transacao.service.actions;

/*
 * @author prandini
 * created 4/29/24
 */

import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.domain.TransacaoStatusEnum;
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

    public Transacao pagar(Long id) {
        log.info(String.format("Pagando transação %s.", id));

        Transacao transacao = getter.byId(id);

        validator.validarPagamento(transacao);

        transacao.setStatus(TransacaoStatusEnum.PAGO);
        transacao.setDtPagamento(LocalDateTime.now());

        if(transacao.getProxima() == null)
            transacao.getLancamento().setQuitado(true);

        return repository.save(transacao);
    }
}
