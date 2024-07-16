package com.prandini.smartwallet.lancamento.converter;


import com.prandini.smartwallet.common.LocalDateConverter;
import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.lancamento.domain.dto.LancamentoOutput;

/*
 * @author prandini
 * created 4/16/24
 */
public class LancamentoConverter {

    public static LancamentoOutput toOutput(Lancamento lancamento){
        return LancamentoOutput.builder()
                .id(lancamento.getId())
                .tipoLancamento(lancamento.getTipoLancamento())
                .categoriaLancamento(lancamento.getCategoriaLancamento())
                .tipoPagamento(lancamento.getTipoPagamento())
                .valor(lancamento.getValor())
                .dtCriacao(LocalDateConverter.toBrazilianDateTimeString(lancamento.getDtCriacao()))
                .conta(lancamento.getConta().getNome())
                .banco(lancamento.getConta().getBanco())
                .parcelas(lancamento.getParcelas())
                .descricao(lancamento.getDescricao())
                .icone(lancamento.getCategoriaLancamento().icone)
                .build();
    }
}
