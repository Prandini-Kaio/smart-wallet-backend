package com.prandini.smartwallet.lancamento.service;

/*
 * @author prandini
 * created 12/18/24
 */

import com.prandini.smartwallet.lancamento.model.SaldoProjetadoOutput;
import com.prandini.smartwallet.lancamento.model.SaldoProjetadoFilter;
import com.prandini.smartwallet.lancamento.service.actions.LancamentoGetter;
import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.model.TransacaoFilter;
import com.prandini.smartwallet.transacao.service.actions.TransacaoGetter;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SaldoProjetadoService {

    @Resource
    private LancamentoGetter lancamentoGetter;

    @Resource
    private TransacaoGetter transacaoGetter;

    public List<SaldoProjetadoOutput> getSaldoProjetado(SaldoProjetadoFilter filter) {
        List<SaldoProjetadoOutput> saldoProjetados = new ArrayList<>();

        List<Transacao> transacoes = transacaoGetter.byFilter(TransacaoFilter.builder()
                .categorias(filter.getCategorias())
                .tipo(filter.getTipo())
                .pagamento(filter.getPagamento())
                .status(filter.getStatus())
                .contaIds(filter.getContaIds())
                .dtInicio(filter.getDtInicio())
                .dtFim(filter.getDtFim())
                .build()
        );

        Map<Month, List<Transacao>> transacoesPorMes = transacoes.stream()
                .collect(
                        java.util.stream.Collectors.groupingBy(
                                transacao -> transacao.getDtVencimento().getMonth()
                        )
                );

        BigDecimal valorEntradasResidual = BigDecimal.ZERO;

        for (Month mes : Month.values()) {
            List<Transacao> transacoesMes = transacoesPorMes.get(mes);

            BigDecimal entradas = transacoesMes == null
                    ? BigDecimal.ZERO
                    : transacoesMes.stream()
                    .filter(transacao -> transacao.getLancamento().isEntrada())
                    .map(Transacao::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal saidas = transacoesMes == null
                    ? BigDecimal.ZERO
                    : transacoesMes.stream()
                    .filter(transacao -> !transacao.getLancamento().isEntrada())
                    .map(Transacao::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            valorEntradasResidual = saldoProjetados.isEmpty()
                    ? entradas.subtract(saidas)
                    : valorEntradasResidual.add(entradas).subtract(saidas);

            saldoProjetados.add(SaldoProjetadoOutput.builder()
                    .mes(mes.getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale.forLanguageTag("pt-BR")))
                    .entradas(entradas)
                    .saidas(saidas)
                    .saldo(valorEntradasResidual)
                    .build()
            );
        }

        return saldoProjetados;
    }
}
