package com.prandini.smartwallet.lancamento.service.actions;

import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.conta.service.actions.ContaGetter;
import com.prandini.smartwallet.conta.service.actions.ContaUpdater;
import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.lancamento.domain.StatusLancamento;
import com.prandini.smartwallet.lancamento.model.LancamentoInput;
import com.prandini.smartwallet.lancamento.repository.LancamentoRepository;
import com.prandini.smartwallet.transacao.service.actions.TransacaoCreator;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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

    public Lancamento create(LancamentoInput input) {
        log.info("Criando lançamento.");

        validator.validarCriacao(input);

        Conta conta = contaGetter.getContaByFilter(input.getConta());

        Lancamento lancamento = repository.save(buildLancamento(input, conta));
        transacaoCreator.create(lancamento);

        return lancamento;
    }

    private Lancamento buildLancamento(LancamentoInput input, Conta conta){
        return Lancamento.builder()
                .tipoLancamento(input.getTipoLancamento())
                .categoriaLancamento(input.getCategoriaLancamento())
                .tipoPagamento(input.getTipoPagamento())
                .valor(input.getValor())
                .dtCriacao(LocalDateTime.now())
                .parcelas(input.getParcelas())
                .conta(conta)
                .descricao(input.getDescricao())
                .status(StatusLancamento.EM_ABERTO)
                .build();

    }

}
