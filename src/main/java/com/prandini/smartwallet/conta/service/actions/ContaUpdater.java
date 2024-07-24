package com.prandini.smartwallet.conta.service.actions;

/*
 * @author prandini
 * created 4/26/24
 */

import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.conta.repository.ContaRepository;
import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.lancamento.domain.TipoLancamentoEnum;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@CommonsLog
public class ContaUpdater {

    @Resource
    private ContaRepository repository;

    public void atualizaLancamento(Long id, Lancamento lancamento){
        log.info("Atualizando lançamentos.");
    }
}
