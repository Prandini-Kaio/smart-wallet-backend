package com.prandini.smartwallet.transacao.service;

import com.prandini.smartwallet.common.model.TotalizadorFinanceiro;
import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.lancamento.domain.StatusLancamento;
import com.prandini.smartwallet.transacao.converter.TransacaoConverter;
import com.prandini.smartwallet.transacao.domain.StatusTransacaoEnum;
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
import java.time.LocalDateTime;
import java.util.Comparator;
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

    public TotalizadorFinanceiro findTotalizadorByFilter(TransacaoFilter filter) {
        return this.getter.totalizadorByFilter(filter);
    }

    public List<TransacaoOutput> findByFilter(TransacaoFilter filter) {
        return this.getter.byFilter(filter).stream().map(TransacaoConverter::toOutput).toList();
    }

    public void updateStatus(Lancamento lancamento) {
        log.info("Iniciando atualização de status de transações");

        List<Transacao> transacoes = getter.byIdLancamento(lancamento.getId());

        LocalDateTime vencimentoConta = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), lancamento.getConta().getDiaVencimento(), 23, 59, 59);

        transacoes.forEach(transacao -> {
            if(transacao.getStatus().equals(StatusTransacaoEnum.PENDENTE)){
                if(transacao.getDtVencimento().isBefore(vencimentoConta)) {
                    log.info(String.format("Alterando status da transacao %s do lancamento %s para ATRASADO", transacao.getDescricao(), lancamento.getId()));
                    transacao.setStatus(StatusTransacaoEnum.ATRASADO);
                    this.updater.update(transacao);
                }
            }
        });
    }

    public List<TransacaoOutput> pagarTransacoes(TransacaoFilter filter) {
        log.info("Iniciando pagamento de transações a partir de um filtro");
        List<Transacao> transacoes = getter.byFilter(filter)
                .stream().sorted(Comparator.comparing(Transacao::getDtVencimento)).toList();
        return this.updater.pagarTodos(transacoes).stream().map(TransacaoConverter::toOutput).collect(Collectors.toList());
    }
}
