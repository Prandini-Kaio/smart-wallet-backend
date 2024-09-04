package com.prandini.smartwallet.lancamento.service.tasks;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author kaiooliveira
 * created 04/09/2024
 */

@Component
@CommonsLog
public class LancamentoContasFixasTask {


    @Scheduled(timeUnit = TimeUnit.DAYS, fixedRate = 1L)
    public void tst(){

    }
}
