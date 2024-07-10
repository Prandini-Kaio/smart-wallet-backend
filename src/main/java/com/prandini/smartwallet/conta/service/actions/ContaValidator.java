package com.prandini.smartwallet.conta.service.actions;

/*
 * @author prandini
 * created 4/5/24
 */

import com.prandini.smartwallet.common.exception.BusinessException;
import com.prandini.smartwallet.common.exception.CommonExceptionMessages;
import com.prandini.smartwallet.conta.model.ContaInput;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

@Component
@CommonsLog
public class ContaValidator {

    @Resource
    private ContaGetter getter;

    public void validarCriacao(ContaInput input){
        this.validarContaExistente(input);
    }

    private void validarContaExistente(ContaInput input){
        if(getter.existsContaByNomeBanco(input)){
            throw new BusinessException(CommonExceptionMessages.jaExistente("Conta"));
        }
    }
}
