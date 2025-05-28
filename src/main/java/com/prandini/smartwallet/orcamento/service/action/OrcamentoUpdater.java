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
public class OrcamentoUpdater {

    @Resource
    private OrcamentoRepository repository;

    @Resource
    private OrcamentoGetter getter;

    public Orcamento atualizarOrcamento(OrcamentoInput input) {
        log.info("Atualizando orçamento.");

        Orcamento origin = this.getter.byId(input.getId());
        origin.setCategoria(input.getCategoria());
        origin.setValor(input.getValor());
        origin.setMes(input.getMes());

        return this.repository.save(origin);
    }
}
