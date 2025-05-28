package com.prandini.smartwallet.fluxoCaixa.model;

import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.conta.model.ContaOutput;
import com.prandini.smartwallet.lancamento.domain.CategoriaLancamentoEnum;
import com.prandini.smartwallet.lancamento.domain.TipoLancamentoEnum;
import com.prandini.smartwallet.lancamento.domain.TipoPagamentoEnum;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author kaiooliveira
 * created 04/01/2025
 */

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class LancamentosProjetadosOutput {

    private ContaOutput conta;

    private CategoriaLancamentoEnum categoria;

    private String dtVencimento;

    private String descricao;

    private String status;

    @Builder.Default
    private BigDecimal entradas = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal saidas = BigDecimal.ZERO;

    @Hidden
    public void addEntrada(BigDecimal entrada){
        this.entradas = this.entradas.add(entrada);
    }

    @Hidden
    public void addSaida(BigDecimal saida){
        this.saidas = this.saidas.add(saida);
    }

    @Hidden
    public void addValor(BigDecimal valor, TipoLancamentoEnum tipoLancamento){
        if(tipoLancamento.equals(TipoLancamentoEnum.SAIDA)){
            this.addSaida(valor);
        }
        if(tipoLancamento.equals(TipoLancamentoEnum.ENTRADA)){
            this.addEntrada(valor);
        }
    }

}
