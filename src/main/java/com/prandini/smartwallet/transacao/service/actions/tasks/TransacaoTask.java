package com.prandini.smartwallet.transacao.service.actions.tasks;

/*
 * @author prandini
 * created 9/5/24
 */

import com.prandini.smartwallet.transacao.domain.StatusTransacaoEnum;
import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.model.TransacaoFilter;
import com.prandini.smartwallet.transacao.service.actions.TransacaoGetter;
import com.prandini.smartwallet.transacao.service.actions.TransacaoUpdater;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@CommonsLog
public class TransacaoTask {


    @Resource
    private TransacaoGetter getter;

    @Resource
    private TransacaoUpdater updater;

    @Scheduled(cron = "59 59 23 * * *")
    public void atualizarStatus(){
        log.info("Iniciando atualização de status para transações.");

        TransacaoFilter filter = TransacaoFilter.builder().build();

        List<Transacao> transacoes = getter.byFilter(filter);

        transacoes.stream()
                .filter(t -> t.getStatus() != StatusTransacaoEnum.CANCELADO && t.getStatus() != StatusTransacaoEnum.ATRASADO)
                .forEach(t -> {
                    if(t.getDtVencimento().isBefore(LocalDateTime.now())){
                        log.info(String.format("Transação com ID %s atualizada para status ATRASADO.", t.getId()));
                        t.setStatus(StatusTransacaoEnum.ATRASADO);
                        updater.update(t);
                    }
                });
    }
}
