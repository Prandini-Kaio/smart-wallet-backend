package com.prandini.smartwallet.transacao.model;

import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.transacao.domain.Transacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author kaiooliveira
 * created 16/02/2025
 */

@Data
@AllArgsConstructor @NoArgsConstructor
public class TransacaoPagamentoEvent {

    public BigDecimal valor;

    public Conta conta;
}
