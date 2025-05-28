package com.prandini.smartwallet.lancamento.converter;


import com.prandini.smartwallet.common.utils.DateUtils;
import com.prandini.smartwallet.conta.converter.ContaConverter;
import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.lancamento.model.LancamentoOutput;
import com.prandini.smartwallet.transacao.converter.TransacaoConverter;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/*
 * @author prandini
 * created 4/16/24
 */

@Component
public class LancamentoConverter {

    @Resource
    private ContaConverter contaConverter;

    public LancamentoOutput toOutput(Lancamento lancamento){
        return LancamentoOutput.builder()
                .id(lancamento.getId())
                .tipoLancamento(lancamento.getTipoLancamento().getDescricao())
                .categoriaLancamento(lancamento.getCategoriaLancamento().getNome())
                .tipoPagamento(lancamento.getTipoPagamento().getDescricao())
                .valor(lancamento.getValorBruto())
                .dtCriacao(DateUtils.toBrazilianDateTimeString(lancamento.getDtCriacao()))
                .contaDestino(contaConverter.toOutput(lancamento.getContaDestino()))
                .contaOrigem(lancamento.getContaOrigem() != null ? contaConverter.toOutput(lancamento.getContaOrigem()) : null)
                .parcelas(lancamento.getParcelas())
                .descricao(lancamento.getDescricao())
                .status(lancamento.getStatus().getDescricao())
                .icone(lancamento.getCategoriaLancamento().icone)
                .transacoes(TransacaoConverter.toListOutputs(lancamento.getTransacoes()))
                    .build();
    }
}
