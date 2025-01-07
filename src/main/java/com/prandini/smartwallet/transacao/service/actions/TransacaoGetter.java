package com.prandini.smartwallet.transacao.service.actions;

/*
 * @author prandini
 * created 4/29/24
 */

import com.prandini.smartwallet.common.exception.CommonExceptionSupplier;
import com.prandini.smartwallet.common.model.TotalizadorFinanceiro;
import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.lancamento.model.ResumoFinanceiroFilter;
import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.domain.dto.TransacaoOutput;
import com.prandini.smartwallet.transacao.model.TransacaoFilter;
import com.prandini.smartwallet.transacao.repository.TransacaoRepository;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
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

    public List<Transacao> byIdLancamento(Long idLancamento) {
        return repository.findByIdLancamento(idLancamento);
    }

    public Transacao byId(Long id) {
        return this.repository.findById(id).orElseThrow(CommonExceptionSupplier.naoEncontrado("Transação"));
    }

    public List<Transacao> byFilter(TransacaoFilter filter){
        log.info(String.format("Consultando transações por filtro %s.", filter));

        return this.repository.getTransacoesByFilter(filter);
    }

    public TotalizadorFinanceiro totalizadorByFilter(TransacaoFilter filter) {
        log.info(String.format("Consultando de totalizadores a partir do filtro %s.", filter));

        List<Transacao> transacoes = this.byFilter(filter);

        return TotalizadorFinanceiro.calcularTransacao(transacoes);
    }

    public boolean hasTransacaoVencida(Long idLancamento){
        return this.repository.hasTransacaoVencida(idLancamento);
    }

    public List<Transacao> byContasMes(List<Conta> contas, Month mes) {
        List<Transacao> transacoes = new ArrayList<>();

        for (Conta conta : contas) {
            transacoes.addAll(this.repository.byVencimentoConta(conta, mes));
        }

        return transacoes;
    }
}
