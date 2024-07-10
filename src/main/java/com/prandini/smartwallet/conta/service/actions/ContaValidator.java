package com.prandini.smartwallet.conta.service.actions;

/*
 * @author prandini
 * created 4/5/24
 */

import com.prandini.smartwallet.conta.model.ContaInput;
import com.prandini.smartwallet.conta.repository.ContaRepository;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

@Component
@CommonsLog
public class ContaValidator {

    @Resource
    private ContaRepository repository;

    public void validarCriacao(ContaInput input){
        log.info("Implementar validator criacao conta!");
    }
}
