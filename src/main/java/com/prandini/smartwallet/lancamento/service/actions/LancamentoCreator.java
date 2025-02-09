package com.prandini.smartwallet.lancamento.service.actions;

import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.conta.model.ContaFilter;
import com.prandini.smartwallet.conta.service.actions.ContaGetter;
import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.lancamento.domain.StatusLancamento;
import com.prandini.smartwallet.lancamento.model.LancamentoInput;
import com.prandini.smartwallet.lancamento.repository.LancamentoRepository;
import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.service.actions.TransacaoCreator;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/*
 * @author prandini
 * created 4/16/24
 */

@Component
@CommonsLog
public class LancamentoCreator {

    @Resource
    private LancamentoRepository repository;

    @Resource
    private ContaGetter contaGetter;

    @Resource
    private TransacaoCreator transacaoCreator;

    @Resource
    private LancamentoValidator validator;

    @Resource
    private ApplicationEventPublisher publisher;

    public Lancamento create(LancamentoInput input) {
        log.info("Criando lançamento.");

        Conta contaDestino = contaGetter.byId(input.getContaDestinoId());
        Conta contaOrigem = input.getContaOrigemId() != null ? contaGetter.byId(input.getContaOrigemId()) : null;

        this.validator.validarCriacao(input, contaDestino, contaOrigem);

        Lancamento lancamento = buildLancamento(input, contaDestino, contaOrigem);
        List<Transacao> transacoes = transacaoCreator.create(lancamento);
        transacoes.forEach(transacao -> transacao.setLancamento(lancamento));
        lancamento.setTransacoes(transacoes);

        this.publisher.publishEvent(lancamento);

        return this.repository.save(lancamento);
    }

    private Lancamento buildLancamento(LancamentoInput input, Conta contaDestino, Conta contaOrigem){
        return Lancamento.builder()
                .tipoLancamento(input.getTipoLancamento())
                .categoriaLancamento(input.getCategoriaLancamento())
                .tipoPagamento(input.getTipoPagamento())
                .status(input.getStatus() != null ? input.getStatus() : StatusLancamento.EM_ABERTO)
                .valorBruto(input.getValor())
                .dtCriacao(input.getDtCriacao() != null ? input.getDtCriacao() : LocalDateTime.now())
                .dtAlteracaoStatus(input.getDtAlteracaoStatus() != null ? input.getDtAlteracaoStatus() : null)
                .parcelas(input.getParcelas())
                .contaDestino(contaDestino)
                .contaOrigem(contaOrigem)
                .descricao(input.getDescricao())
                .build();

    }

    public Lancamento fromInput(LancamentoInput input) {
        log.info("Criando lancamento output atraves de um input de lançamento.");

        Conta contaDestino = contaGetter.byId(input.getContaDestinoId());
        Conta contaOrigem = input.getContaOrigemId() != null ? contaGetter.byId(input.getContaOrigemId()) : null;

        Lancamento lancamento = buildLancamento(input, contaDestino, contaOrigem);
        List<Transacao> transacoes = transacaoCreator.create(lancamento);
        transacoes.forEach(transacao -> transacao.setLancamento(lancamento));
        lancamento.setTransacoes(transacoes);

        return lancamento;
    }
}
