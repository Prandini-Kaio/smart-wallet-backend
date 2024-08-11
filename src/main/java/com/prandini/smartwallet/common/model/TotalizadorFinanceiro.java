package com.prandini.smartwallet.common.model;

import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.lancamento.domain.TipoLancamentoEnum;
import com.prandini.smartwallet.transacao.domain.Transacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author kaiooliveira
 * created 22/07/2024
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalizadorFinanceiro {

    private BigDecimal total;

    private BigDecimal totalEntrada;

    private BigDecimal totalSaida;

    public static TotalizadorFinanceiro calcularTotalizador(List<Lancamento> lancamentos) {
        BigDecimal totalSaida;
        BigDecimal totalEntrada;
        BigDecimal total;

        totalSaida = lancamentos.stream()
                .filter(l -> l.getTipoLancamento().equals(TipoLancamentoEnum.SAIDA))
                .map(Lancamento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        totalEntrada = lancamentos.stream()
                .filter(l -> l.getTipoLancamento().equals(TipoLancamentoEnum.ENTRADA))
                .map(Lancamento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        total = totalEntrada.subtract(totalSaida);

        return new TotalizadorFinanceiro(total, totalEntrada, totalSaida);
    }

    public static TotalizadorFinanceiro calcularTransacao(List<Transacao> transacoes) {
        BigDecimal totalSaida;
        BigDecimal totalEntrada;
        BigDecimal total;

        totalSaida = transacoes.stream()
                .filter(t -> t.getLancamento().getTipoLancamento().equals(TipoLancamentoEnum.SAIDA))
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        totalEntrada = transacoes.stream()
                .filter(l -> l.getLancamento().getTipoLancamento().equals(TipoLancamentoEnum.ENTRADA))
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        total = totalEntrada.subtract(totalSaida);

        return new TotalizadorFinanceiro(total, totalEntrada, totalSaida);
    }
}
