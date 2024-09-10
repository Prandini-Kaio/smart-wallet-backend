package com.prandini.smartwallet.lancamento.service.tasks;

import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.lancamento.model.LancamentoInput;
import com.prandini.smartwallet.lancamento.service.LancamentoService;
import com.prandini.smartwallet.lancamento.service.actions.LancamentoGetter;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author kaiooliveira
 * created 04/09/2024
 */

@Component
@CommonsLog
public class LancamentoTask {

    @Resource
    private LancamentoService service;

    @Resource
    private LancamentoGetter getter;

    @Scheduled(cron = "0 0 0 * * *")
    private void atualizarStatus() {

        log.info("Task de lançamento iniciada");

        List<Lancamento> lancamentos = getter.findTodos();

        lancamentos.forEach(service::updateStatus);
    }
}
