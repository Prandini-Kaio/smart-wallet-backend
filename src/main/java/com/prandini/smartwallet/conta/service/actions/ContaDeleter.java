package com.prandini.smartwallet.conta.service.actions;

/*
 * @author prandini
 * created 12/29/24
 */

import com.prandini.smartwallet.conta.repository.ContaRepository;
import com.prandini.smartwallet.lancamento.service.actions.LancamentoDeleter;
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

    @Resource
    private LancamentoDeleter lancamentoDeleter;

    public void deletar(Long id){
        log.info(String.format("Deletando conta com id %s", id));
        this.lancamentoDeleter.deleteByConta(id);
        this.repository.deleteById(id);
    }
}
