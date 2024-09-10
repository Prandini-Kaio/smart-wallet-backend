package com.prandini.smartwallet.transacao.service.actions;

/*
 * @author prandini
 * created 4/29/24
 */

import com.prandini.smartwallet.common.exception.CommonExceptionSupplier;
import com.prandini.smartwallet.common.model.TotalizadorFinanceiro;
import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.domain.dto.TransacaoOutput;
import com.prandini.smartwallet.transacao.model.TransacaoFilter;
import com.prandini.smartwallet.transacao.repository.TransacaoRepository;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@CommonsLog
public class TransacaoGetter {

    @Resource
    private TransacaoRepository repository;

    public List<Transacao> byMonth(Integer month){
        log.info(String.format("Consulta a transações do mês %s.", month));

        return repository.findByVencimento(month);
    }

    public List<Transacao> byStringFilter(String filter){
        log.info(String.format("Consulta a transações com filtro %s.", filter));

        return repository.findByStringFilter(filter);
    }

    public List<Transacao> byIdLancamento(Long idLancamento) {
        return repository.findByIdLancamento(idLancamento);
    }

    public Transacao byId(Long id) {
        return this.repository.findById(id).orElseThrow(CommonExceptionSupplier.naoEncontrado("Transação"));
    }

    public List<Transacao> byFilter(TransacaoFilter filter){
        return this.repository.getTransacoesByFilter(filter);
    }

    public TotalizadorFinanceiro totalizadorByPeriodo(String conta, LocalDate dtInicio, LocalDate dtFim) {
        log.info(String.format("Consultando transações no periodo [%s] até [%s] da conta [%s].", dtInicio, dtFim, conta));

        List<Transacao> transacoes = repository.getByPeriodo(conta, dtInicio, dtFim);

        return TotalizadorFinanceiro.calcularTransacao(transacoes);
    }

    public TotalizadorFinanceiro totalizadorByFilter(TransacaoFilter filter) {
        List<Transacao> transacoes = this.byFilter(filter);

        return TotalizadorFinanceiro.calcularTransacao(transacoes);
    }

    public boolean hasTransacaoVencida(Long idLancamento){
        return this.repository.hasTransacaoVencida(idLancamento);
    }
}
