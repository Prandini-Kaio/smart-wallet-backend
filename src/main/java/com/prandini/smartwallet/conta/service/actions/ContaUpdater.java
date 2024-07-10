package com.prandini.smartwallet.conta.service.actions;

/*
 * @author prandini
 * created 4/26/24
 */

import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.conta.repository.ContaRepository;
import com.prandini.smartwallet.lancamento.domain.Lancamento;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@CommonsLog
public class ContaUpdater {

    @Resource
    private ContaRepository repository;

    public void atualizaLancamentoSaida(Long id, Lancamento lancamento){
        log.info("Atualizando lancamentos de saida.");
        log.info("REVER LOGICA");

        Conta conta = repository.getReferenceById(id);

        conta.getLancamentos().add(lancamento);

        repository.save(conta);
    }

    public void atualizaLancamentoEntrada(Long id, BigDecimal valor){
        log.info("Atualizando lancamentos de entrada.");
        log.info("REVER LOGICA");

        Conta conta = repository.getReferenceById(id);

        conta.setSaldoParcial(conta.getSaldoParcial().add(valor));

        repository.save(conta);
    }
}
