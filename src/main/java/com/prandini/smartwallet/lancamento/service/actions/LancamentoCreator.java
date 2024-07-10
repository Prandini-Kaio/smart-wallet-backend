package com.prandini.smartwallet.lancamento.service.actions;

import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.conta.service.actions.ContaGetter;
import com.prandini.smartwallet.conta.service.actions.ContaUpdater;
import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.lancamento.domain.TipoLancamentoEnum;
import com.prandini.smartwallet.lancamento.domain.TipoPagamentoEnum;
import com.prandini.smartwallet.lancamento.domain.dto.LancamentoInput;
import com.prandini.smartwallet.lancamento.repository.LancamentoRepository;
import com.prandini.smartwallet.transacao.service.actions.TransacaoCreator;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/*
 * @author prandini
 * created 4/16/24
 */

@Component
public class LancamentoCreator {

    @Resource
    private LancamentoRepository repository;

    @Resource
    private ContaGetter contaGetter;

    @Resource
    private ContaUpdater contaUpdater;

    @Resource
    private TransacaoCreator transacaoCreator;

    @Resource
    private LancamentoValidator validator;

    public Lancamento criarLancamento(LancamentoInput input) {

        validator.validarCriacao(input);

        Conta conta = contaGetter.getContaByFilter(input.getConta());

        Lancamento lancamento = repository.save(buildLancamento(input, conta));

        transacaoCreator.criarTransacoes(lancamento);

        if(input.getTipoLancamento() == TipoLancamentoEnum.SAIDA)
            contaUpdater.atualizaLancamentoSaida(conta.getId(), lancamento);
        else{
            contaUpdater.atualizaLancamentoEntrada(conta.getId(), lancamento.getValor());
        }

        return lancamento;
    }

    private Lancamento buildLancamento(LancamentoInput input, Conta conta){
        return Lancamento.builder()
                .tipoLancamento(input.getTipoLancamento())
                .categoriaLancamento(input.getCategoriaLancamento())
                .tipoPagamento(input.isDebito() ? TipoPagamentoEnum.DEBITO : TipoPagamentoEnum.CREDITO)
                .valor(input.getValor())
                .dtCriacao(LocalDateTime.now())
                .parcelas(input.getParcelas())
                .conta(conta)
                .descricao(input.getDescricao())
                .icone(input.getIcone())
                .build();

    }

}
