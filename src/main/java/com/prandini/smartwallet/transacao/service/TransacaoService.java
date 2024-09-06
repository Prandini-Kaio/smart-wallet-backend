package com.prandini.smartwallet.transacao.service;

import com.prandini.smartwallet.common.model.TotalizadorFinanceiro;
import com.prandini.smartwallet.transacao.converter.TransacaoConverter;
import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.domain.dto.TransacaoOutput;
import com.prandini.smartwallet.transacao.model.TransacaoFilter;
import com.prandini.smartwallet.transacao.service.actions.TransacaoGetter;
import com.prandini.smartwallet.transacao.service.actions.TransacaoUpdater;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public TransacaoOutput update(Transacao transacao) {
        return TransacaoConverter.toOutput(this.updater.update(transacao));
    }

    public Page<TransacaoOutput> findByMonth(Integer month){
        log.info(String.format("Iniciando consulta a transações do mês %s.", month));

        return new PageImpl<>(getter.byMonth(month))
                .map(TransacaoConverter::toOutput);
    }

    public Page<TransacaoOutput> findByStringFilter(String filter){
        log.info(String.format("Iniciando consulta a transações com filtro %s.", filter));

        return new PageImpl<>(getter.byStringFilter(filter))
                .map(TransacaoConverter::toOutput);
    }

    public List<TransacaoOutput> findByIdLancamento(Long idLancamento) {
        return getter.byIdLancamento(idLancamento).stream()
                .map(TransacaoConverter::toOutput).collect(Collectors.toList());
    }

    public TotalizadorFinanceiro findTotalizadorFinanceiro(String conta, LocalDate dtInicio, LocalDate dtFim) {
        return this.getter.totalizadorByPeriodo(conta, dtInicio, dtFim);
    }

    public TotalizadorFinanceiro findTotalizadorByFilter(TransacaoFilter filter) {
        return this.getter.totalizadorByFilter(filter);
    }

    public List<TransacaoOutput> findByFilter(TransacaoFilter filter) {
        return this.getter.byFilter(filter).stream().map(TransacaoConverter::toOutput).toList();
    }
}
