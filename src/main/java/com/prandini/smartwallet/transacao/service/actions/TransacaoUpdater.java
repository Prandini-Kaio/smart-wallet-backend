package com.prandini.smartwallet.transacao.service.actions;

/*
 * @author prandini
 * created 4/29/24
 */

import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.domain.TransacaoStatusEnum;
import com.prandini.smartwallet.transacao.repository.TransacaoRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class TransacaoUpdater {

    @Resource
    private TransacaoRepository repository;

    public Transacao pagarTransacao(Long id) {
        Transacao transacao = repository.getReferenceById(id);

        transacao.setStatus(TransacaoStatusEnum.PAGO);

        return repository.save(transacao);
    }
}
