package com.prandini.smartwallet.transacao.service;

import com.prandini.smartwallet.transacao.converter.TransacaoConverter;
import com.prandini.smartwallet.transacao.domain.dto.TransacaoOutput;
import com.prandini.smartwallet.transacao.service.actions.TransacaoGetter;
import com.prandini.smartwallet.transacao.service.actions.TransacaoUpdater;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/*
 * @author prandini
 * created 4/17/24
 */

@Service
@CommonsLog
public class TransacaoService {

    @Resource
    private TransacaoGetter getter;

    @Resource
    private TransacaoUpdater updater;


    public TransacaoOutput pagarTransacao(Long id) {
        log.info(String.format("Iniciando pagamento da transação %s.", id));

        return TransacaoConverter.toOutput(updater.pagar(id));
    }

    public Page<TransacaoOutput> findByMonth(Integer month){
        log.info(String.format("Iniciando consulta a transações do mês %s.", month));

        return new PageImpl<>(getter.findByMonth(month))
                .map(TransacaoConverter::toOutput);
    }

    public Page<TransacaoOutput> findByStringFilter(String filter){
        log.info(String.format("Iniciando consulta a transações com filtro %s.", filter));

        return new PageImpl<>(getter.findByStringFilter(filter))
                .map(TransacaoConverter::toOutput);
    }


    public List<TransacaoOutput> findByIdLancamento(Long idLancamento) {
        return getter.findByIdLancamento(idLancamento).stream()
                .map(TransacaoConverter::toOutput).collect(Collectors.toList());
    }
}
