package com.prandini.smartwallet.common.model;

/*
 * @author prandini
 * created 8/15/24
 */

import com.prandini.smartwallet.conta.model.ContaOutput;
import com.prandini.smartwallet.lancamento.domain.TipoLancamentoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Month;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class ResumoFinanceiroOutput {

    private ContaOutput conta;

    @Builder.Default
    private BigDecimal entradas = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal saidas = BigDecimal.ZERO;

    private Month mes;



    public void addValor(BigDecimal valor, TipoLancamentoEnum tipo) {
        if(tipo.equals(TipoLancamentoEnum.ENTRADA)){
            this.entradas = this.entradas.add(valor);
        }
        if(tipo.equals(TipoLancamentoEnum.SAIDA)){
            this.saidas = this.saidas.add(valor);
        }
    }
}
