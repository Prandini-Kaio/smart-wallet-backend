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

        if(lancamento.getTipoLancamento().equals(TipoLancamentoEnum.ENTRADA)){
            atualizaLancamentoEntrada(id, lancamento.getValor());
        }

        if(lancamento.getTipoLancamento().equals(TipoLancamentoEnum.SAIDA)){
            atualizaLancamentoSaida(id, lancamento);
        }
    }

    private void atualizaLancamentoSaida(Long id, Lancamento lancamento){
        Conta conta = repository.getReferenceById(id);

        log.info(String.format("Atualizando lancamentos de saida da conta %s - %s.", conta.getBanco(), conta.getNome()));
        log.info("REVER LOGICA");

        conta.getLancamentos().add(lancamento);

        repository.save(conta);
    }

    private void atualizaLancamentoEntrada(Long id, BigDecimal valor){
        Conta conta = repository.getReferenceById(id);

        log.info(String.format("Atualizando lancamentos de entrada da conta %s - %s.", conta.getBanco(), conta.getNome()));

        conta.setSaldoParcial(conta.getSaldoParcial().add(valor));

        repository.save(conta);
    }
}
