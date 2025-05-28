package com.prandini.smartwallet.transacao.model;

import com.prandini.smartwallet.conta.model.ContaInput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author kaiooliveira
 * created 13/02/2025
 */

@Data
@AllArgsConstructor @NoArgsConstructor
public class TransacaoPagamentoInput {

    private List<Long> ids;

    private Long contaDestinoId;
}
