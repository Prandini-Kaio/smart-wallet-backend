package com.prandini.smartwallet.transacao.service.actions;

/*
 * @author prandini
 * created 4/29/24
 */

import com.prandini.smartwallet.common.exception.BusinessException;
import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.conta.service.actions.ContaGetter;
import com.prandini.smartwallet.lancamento.service.actions.LancamentoCreator;
import com.prandini.smartwallet.lancamento.service.actions.LancamentoUpdater;
import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.domain.StatusTransacaoEnum;
import com.prandini.smartwallet.transacao.model.TransacaoPagamentoEvent;
import com.prandini.smartwallet.transacao.model.TransacaoPagamentoInput;
import com.prandini.smartwallet.transacao.repository.TransacaoRepository;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @Resource
    private LancamentoCreator lancamentoCreator;

    @Resource
    private ContaGetter contaGetter;

    @Resource
    private ApplicationEventPublisher publisher;

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

    public List<Transacao> pagar(TransacaoPagamentoInput input) {
        log.info(String.format("Pagando transações %s.", input.getIds()));

        List<Transacao> transacoes = getter.byIdsIn(input.getIds());

        BigDecimal valorPagamento = BigDecimal.ZERO;
        String contas = transacoes.stream()
                .map(t -> t.getLancamento().getContaDestino().getBancoNome())
                .distinct()
                .collect(Collectors.joining(", "));


        Conta conta = contaGetter.byId(input.getContaDestinoId());

        for (Transacao transacao : transacoes) {
            this.validator.validarPagamento(transacao);

            transacao.setStatus(StatusTransacaoEnum.PAGO);
            transacao.setDtPagamento(LocalDateTime.now());

            if(transacao.getProxima() == null)
                lancamentoUpdater.quitarLancamento(transacao.getLancamento().getId());

            valorPagamento = valorPagamento.add(transacao.getValor());
        }

        this.lancamentoCreator.gerarPagamento(valorPagamento, contas, conta);
        this.publisher.publishEvent(new TransacaoPagamentoEvent(valorPagamento, conta));

        return repository.saveAll(transacoes);
    }
}
