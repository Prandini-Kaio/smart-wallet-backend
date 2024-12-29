package com.prandini.smartwallet.transacao.service.actions;

/*
 * @author prandini
 * created 12/25/24
 */

import com.prandini.smartwallet.transacao.repository.TransacaoRepository;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

@Component
@CommonsLog
public class TransacaoDeleter {

    @Resource
    private TransacaoRepository repository;

    public void byLancamento(Long lancamentoID){
        log.info(String.format("deletando todas as transações do lancamento com id %s.", lancamentoID));
        this.repository.deleteProximaByLancamento(lancamentoID);
        this.repository.deleteByLancamento(lancamentoID);
    }

    public void byConta(Long contaId) {
        this.repository.deleteByConta(contaId);
    }
}
