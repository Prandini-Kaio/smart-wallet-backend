package com.prandini.smartwallet.lancamento.model.events;

import com.prandini.smartwallet.lancamento.domain.Lancamento;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author kaiooliveira
 * created 08/02/2025
 */

@Data
@AllArgsConstructor
public class LancamentoEvent {

    private Lancamento lancamento;

}
