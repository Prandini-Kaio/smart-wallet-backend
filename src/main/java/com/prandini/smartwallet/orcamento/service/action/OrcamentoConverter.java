package com.prandini.smartwallet.orcamento.service.action;

import com.prandini.smartwallet.lancamento.domain.CategoriaLancamentoEnum;
import com.prandini.smartwallet.orcamento.domain.Orcamento;
import com.prandini.smartwallet.orcamento.model.OrcamentoOutput;
import com.prandini.smartwallet.transacao.domain.Transacao;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author kaiooliveira
 * created 27/12/2024
 */

@Component
public class OrcamentoConverter {

    public OrcamentoOutput toOutput(Orcamento orcamento){
        return this.toOutput(orcamento, BigDecimal.ZERO);
    }

    public List<OrcamentoOutput> toOutput(List<Orcamento> orcamento, Map<CategoriaLancamentoEnum, List<Transacao>> transacaoMap) {
        return orcamento.stream().map(o -> toOutput(o, transacaoMap.get(o.getCategoria()))).toList();
    }

    public OrcamentoOutput toOutput(Orcamento orcamento, List<Transacao> transacaoMap) {
        BigDecimal gastoAtual = BigDecimal.ZERO;

        if (transacaoMap != null) {
            gastoAtual = transacaoMap.stream()
                    .map(Transacao::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        return this.toOutput(orcamento, gastoAtual);
    }

    public OrcamentoOutput toOutput(Orcamento orcamento, BigDecimal gastoAtual) {

        return OrcamentoOutput.builder()
                .id(orcamento.getId())
                .limite(orcamento.getValor())
                .gastoAtual(gastoAtual)
                .categoria(orcamento.getCategoria())
                .mes(orcamento.getMes())
                .build();
    }

}
