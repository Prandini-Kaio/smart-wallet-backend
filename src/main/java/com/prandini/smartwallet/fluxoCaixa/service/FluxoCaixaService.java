package com.prandini.smartwallet.fluxoCaixa.service;

import com.prandini.smartwallet.fluxoCaixa.model.FluxoCaixaProjetadoFilter;
import com.prandini.smartwallet.fluxoCaixa.model.FluxoCaixaProjetadoOutput;
import com.prandini.smartwallet.fluxoCaixa.service.action.FluxoCaixaProjetadoGetter;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * @author kaiooliveira
 * created 04/01/2025
 */

@Service
public class FluxoCaixaService {

    @Resource
    private FluxoCaixaProjetadoGetter projetadoGetter;

    public FluxoCaixaProjetadoOutput getProjetadoByFilter(FluxoCaixaProjetadoFilter filter) {
        if(filter.getDtInicio() == null && filter.getDtFim() == null){
            filter.setDtInicio(LocalDate.now());
            filter.setDtFim(LocalDate.now());
        }
        return projetadoGetter.getResumoProjetadoByFilter(filter);
    }
}
