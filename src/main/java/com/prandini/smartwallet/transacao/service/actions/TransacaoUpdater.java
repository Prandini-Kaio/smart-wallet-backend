package com.prandini.smartwallet.transacao.service.actions;

/*
 * @author prandini
 * created 4/29/24
 */

import com.prandini.smartwallet.common.exception.BusinessException;
import com.prandini.smartwallet.lancamento.service.actions.LancamentoUpdater;
import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.domain.StatusTransacaoEnum;
import com.prandini.smartwallet.transacao.domain.dto.TransacaoOutput;
import com.prandini.smartwallet.transacao.repository.TransacaoRepository;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@CommonsLog
public class TransacaoUpdater {

    @Resource
    private TransacaoGetter getter;

    @Resource
    private TransacaoRepository repository;

    @Resource
    private TransacaoValidator validator;

    @Resource
    private LancamentoUpdater lancamentoUpdater;

    public Transacao pagar(Long id) {
        log.info(String.format("Pagando transação %s.", id));

        Transacao transacao = getter.byId(id);

        validator.validarPagamento(transacao);

        transacao.setStatus(StatusTransacaoEnum.PAGO);
        transacao.setDtPagamento(LocalDateTime.now());

        if(transacao.getProxima() == null)
            lancamentoUpdater.quitarLancamento(transacao.getLancamento().getId());

        return repository.save(transacao);
    }

    public Transacao update(Transacao transacao) {

        Transacao t = repository.findById(transacao.getId()).orElse(null);

        if(t == null)
            throw new BusinessException("Transacao inexistente");

        t.setValor(transacao.getValor());
        t.setLancamento(t.getLancamento());
        t.setDescricao(transacao.getDescricao());
        t.setStatus(transacao.getStatus());
        t.setDtVencimento(transacao.getDtVencimento());
        t.setDtPagamento(transacao.getDtPagamento());

        return this.repository.save(t);
    }

    public List<Transacao> pagarTodos(List<Transacao> transacoes) {
        log.info("Pagando transações com base em um filtro");
        transacoes.forEach(t -> {
            this.validator.validarPagamento(t);
            this.pagar(t.getId());
        });
        transacoes.forEach(t -> this.pagar(t.getId()));
        log.info("Todas as transações foram pagas com sucesso!");
        return transacoes;
    }
}
