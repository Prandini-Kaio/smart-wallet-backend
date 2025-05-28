package com.prandini.smartwallet.orcamento.service.action;

import com.prandini.smartwallet.common.exception.BusinessException;
import com.prandini.smartwallet.common.exception.CommonExceptionSupplier;
import com.prandini.smartwallet.orcamento.model.OrcamentoInput;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @author kaiooliveira
 * created 30/12/2024
 */
@Component
public class OrcamentoValidator {

    @Resource
    private OrcamentoGetter getter;

    public void validar(OrcamentoInput input){
        this.validarDuplicado(input);
    }

    private void validarDuplicado(OrcamentoInput input) {
        if(this.getter.existsByCategoriaMes(input.getCategoria(), input.getMes())){
            throw new BusinessException(String.format("Já existe um orçamento cadastrado para a categoria %s no mês %s.", input.getCategoria(), input.getMes()));
        }
    }
}
