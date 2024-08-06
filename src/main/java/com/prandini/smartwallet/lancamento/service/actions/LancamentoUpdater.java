package com.prandini.smartwallet.lancamento.service.actions;

import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.lancamento.domain.StatusLancamento;
import com.prandini.smartwallet.lancamento.repository.LancamentoRepository;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author kaiooliveira
 * created 06/08/2024
 */

@Component
@CommonsLog
public class LancamentoUpdater {

    @Resource
    private LancamentoRepository repository;

    @Resource
    private LancamentoGetter getter;

    public Lancamento quitarLancamento(Long id){
        Lancamento lancamento = getter.byId(id);

        lancamento.setStatus(StatusLancamento.QUITADO);
        lancamento.setDtAlteracaoStatus(LocalDateTime.now());

        return repository.save(lancamento);
    }
}
