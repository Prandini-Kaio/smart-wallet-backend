package com.prandini.smartwallet.orcamento.service.action;

import com.prandini.smartwallet.orcamento.domain.Orcamento;
import com.prandini.smartwallet.orcamento.model.OrcamentoInput;
import com.prandini.smartwallet.orcamento.repository.OrcamentoRepository;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

/**
 * @author kaiooliveira
 * created 27/12/2024
 */

@Component
@CommonsLog
public class OrcamentoCreator {

    @Resource
    private OrcamentoRepository repository;

    public Orcamento criarOrcamento(OrcamentoInput input) {
        log.info("Criando novo orçamento.");

        Orcamento orcamento = Orcamento.builder()
                .valor(input.getValor())
                .categoria(input.getCategoria())
                .mes(input.getMes())
                .build();

        return this.repository.save(orcamento);
    }
}
