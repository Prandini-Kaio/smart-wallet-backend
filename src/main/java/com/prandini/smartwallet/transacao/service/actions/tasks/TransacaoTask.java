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
    /*
    *
    * TASK DE LANCAMENTO ESTA RESPONSAVEL POR ATUALIZAR O STATUS DE AMBAS
    *
    * */
}
