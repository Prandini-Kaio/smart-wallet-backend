package com.prandini.smartwallet.common.model;

/*
 * @author prandini
 * created 8/15/24
 */

import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.lancamento.domain.StatusLancamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor
public class ResumoFinanceiro {

    private BigDecimal saldo;
    private BigDecimal saldoProjetado;
    private BigDecimal saldoDisponivel;
    private BigDecimal saldoDisponivelProjetado;

    public static ResumoFinanceiro calcularResumo(List<Lancamento> lancamentos) {
        BigDecimal saldo = lancamentos.stream()
                .map(Lancamento::getValorBruto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal saldoProjetado = lancamentos.stream()
                .map(Lancamento::getValorProjetado)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal saldoDisponivel = lancamentos.stream()
                .filter(l -> l.getStatus().equals(StatusLancamento.EM_ABERTO))
                .map(Lancamento::getValorBruto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal saldoDisponivelProjetado = lancamentos.stream()
                .filter(l -> l.getStatus().equals(StatusLancamento.EM_ABERTO))
                .map(Lancamento::getValorProjetado)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new ResumoFinanceiro(saldo, saldoProjetado, saldoDisponivel, saldoDisponivelProjetado);
    }
}
