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

@Component
@CommonsLog
public class TransacaoUpdater {

    @Resource
    private TransacaoRepository repository;

    @Resource
    private TransacaoValidator validator;

    public Transacao pagarTransacao(Long id) {
        log.info(String.format("Pagando transação %s.", id));

        validator.validarPagament(id);

        Transacao transacao = repository.getReferenceById(id);

        transacao.setStatus(TransacaoStatusEnum.PAGO);

        return repository.save(transacao);
    }
}
