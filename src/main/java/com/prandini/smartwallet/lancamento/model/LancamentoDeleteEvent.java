package com.prandini.smartwallet.lancamento.model;

import com.prandini.smartwallet.lancamento.domain.Lancamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * @author kaiooliveira
 * created 09/02/2025
 */

@Data
@AllArgsConstructor
public class LancamentoDeleteEvent {

    private Lancamento lancamento;

}
