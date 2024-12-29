package com.prandini.smartwallet.conta.service.actions;

/*
 * @author prandini
 * created 12/29/24
 */

import com.prandini.smartwallet.conta.repository.ContaRepository;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

@Component
@CommonsLog
public class ContaDeleter {

    @Resource
    private ContaRepository repository;

    @Resource
    private ContaValidator validator;

    public void deletar(Long id){
        log.info(String.format("Deletando conta com id %s"));
        this.repository.deleteById(id);
    }
}
