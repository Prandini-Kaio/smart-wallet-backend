package com.prandini.smartwallet.orcamento.service.action;

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
public class OrcamentoDeleter {

    @Resource
    private OrcamentoRepository repository;

    public void deletarOrcamento(Long id) {
        log.info(String.format("Deletando orçamento com id %s.", id));
        this.repository.deleteById(id);
    }
}
