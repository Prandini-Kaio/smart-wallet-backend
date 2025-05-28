package com.prandini.smartwallet.lancamento.service.actions;

import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.conta.service.actions.ContaGetter;
import com.prandini.smartwallet.lancamento.converter.LancamentoConverter;
import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.lancamento.domain.StatusLancamento;
import com.prandini.smartwallet.lancamento.model.LancamentoInput;
import com.prandini.smartwallet.lancamento.model.LancamentoOutput;
import com.prandini.smartwallet.lancamento.repository.LancamentoRepository;
import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.service.actions.TransacaoCreator;
import com.prandini.smartwallet.transacao.service.actions.TransacaoDeleter;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kaiooliveira
 * created 06/08/2024
 */

@Component
@CommonsLog
public class LancamentoUpdater {

    @Resource
    private LancamentoRepository repository;

    @Resource
    private LancamentoGetter getter;

    @Resource
    private TransacaoCreator transacaoCreator;

    @Resource
    private TransacaoDeleter transacaoDeleter;

    @Resource
    private LancamentoValidator validator;

    @Resource
    private ContaGetter contaGetter;

    public Lancamento quitarLancamento(Long id){

        log.info(String.format("Alterando status do lancamento %s para QUITADO", id));

        Lancamento lancamento = getter.byId(id);

        lancamento.setStatus(StatusLancamento.QUITADO);
        lancamento.setDtAlteracaoStatus(LocalDateTime.now());

        return repository.save(lancamento);
    }

    public Lancamento update(Lancamento lancamento){

        log.info(String.format("Alterando lancamento %s", lancamento.getId()));

        this.validator.validarUpdate(lancamento);

        Lancamento origin = getter.byId(lancamento.getId());

        origin.setTipoLancamento(lancamento.getTipoLancamento());
        origin.setCategoriaLancamento(lancamento.getCategoriaLancamento());
        origin.setTipoPagamento(lancamento.getTipoPagamento());
        origin.setStatus(lancamento.getStatus());
        origin.setValorBruto(lancamento.getValorBruto());
        origin.setDtCriacao(lancamento.getDtCriacao());
        origin.setParcelas(lancamento.getParcelas());
        origin.setContaOrigem(lancamento.getContaOrigem());
        origin.setDescricao(lancamento.getDescricao());

        return repository.save(origin);
    }

    public Lancamento fromInput(LancamentoInput input){
        Conta contaDestino = contaGetter.byId(input.getContaDestinoId());
        Conta contaOrigem = contaGetter.byId(input.getContaOrigemId());

        Lancamento lancamento = getter.byId(input.getId());

        this.transacaoDeleter.byLancamento(lancamento.getId());
        List<Transacao> transacoes = transacaoCreator.fromInput(input);
        transacoes.forEach(transacao -> transacao.setLancamento(lancamento));

        lancamento.setValorBruto(input.getValor());
        lancamento.setCategoriaLancamento(input.getCategoriaLancamento());
        lancamento.setTipoLancamento(input.getTipoLancamento());
        lancamento.setTipoPagamento(input.getTipoPagamento());
        lancamento.setStatus(input.getStatus());
        lancamento.setDtCriacao(input.getDtCriacao());
        lancamento.setParcelas(input.getParcelas());
        lancamento.setContaDestino(contaDestino);
        lancamento.setContaOrigem(contaOrigem);
        lancamento.setDescricao(input.getDescricao());
        lancamento.setTransacoes(new ArrayList<>(transacoes));

        return this.repository.save(lancamento);
    }
}
