package com.prandini.smartwallet.fluxoCaixa.model;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kaiooliveira
 * created 04/01/2025
 */


@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class FluxoCaixaProjetadoOutput {

    private BigDecimal saldoAnterior = BigDecimal.ZERO;

    private BigDecimal entradas = BigDecimal.ZERO;

    private BigDecimal saidas = BigDecimal.ZERO;

    private BigDecimal saldoProjetado = BigDecimal.ZERO;

    @Builder.Default
    private List<LancamentosProjetadosOutput> lancamentos = new ArrayList<>();

    @Hidden
    public void addAllLancamentos(List<LancamentosProjetadosOutput> lancs){
        this.lancamentos.addAll(lancs);
    }
}
