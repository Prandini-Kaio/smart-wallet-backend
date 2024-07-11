package com.prandini.smartwallet.transacao.service.actions;

/*
 * @author prandini
 * created 4/29/24
 */

import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.domain.TransacaoFilter;
import com.prandini.smartwallet.transacao.repository.TransacaoRepository;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@CommonsLog
public class TransacaoGetter {

    @Resource
    private TransacaoRepository repository;

    public List<Transacao> findByMonth(Integer month){
        log.info(String.format("Consulta a transações do mês %s.", month));

        return repository.findByVencimento(month);
    }

    public List<Transacao> findByFilter(TransacaoFilter filter){
        log.info("Consulta a transações com filtro.");

        return repository.findByFilter(filter.getStatus(), filter.getMes());
    }

    public List<Transacao> findByStringFilter(String filter){
        log.info(String.format("Consulta a transações com filtro %s.", filter));

        return repository.findByStringFilter(filter);
    }
}
