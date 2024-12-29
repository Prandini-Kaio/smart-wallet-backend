package com.prandini.smartwallet.orcamento.service.action;

import com.prandini.smartwallet.common.exception.CommonExceptionSupplier;
import com.prandini.smartwallet.orcamento.domain.Orcamento;
import com.prandini.smartwallet.orcamento.repository.OrcamentoRepository;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author kaiooliveira
 * created 27/12/2024
 */

@Component
@CommonsLog
public class OrcamentoGetter {

    @Resource
    private OrcamentoRepository repository;

    public List<Orcamento> listarOrcamentos() {
        log.info("Buscando orcamentos cadastrados.");
        return repository.findAll();
    }

    public Orcamento byId(Long id) {
        log.info("Buscando orçamento por id.");
        return repository.findById(id).orElseThrow(CommonExceptionSupplier.naoEncontrado("Orcamento", id.toString()));
    }
}
