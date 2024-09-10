package com.prandini.smartwallet.lancamento.service.tasks;

import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.lancamento.domain.StatusLancamento;
import com.prandini.smartwallet.lancamento.model.LancamentoFilter;
import com.prandini.smartwallet.lancamento.service.actions.LancamentoGetter;
import com.prandini.smartwallet.lancamento.service.actions.LancamentoUpdater;
import com.prandini.smartwallet.transacao.domain.StatusTransacaoEnum;
import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.service.actions.TransacaoGetter;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author kaiooliveira
 * created 04/09/2024
 */

@Component
@CommonsLog
public class LancamentoTask {

    @Resource
    private LancamentoGetter getter;

    @Resource
    private LancamentoUpdater updater;

    @Resource
    private TransacaoGetter transacaoGetter;

    @Scheduled(cron = "0 0 0 * * *")
    private void atualizarStatus(Lancamento lancamento) {

    }

}
