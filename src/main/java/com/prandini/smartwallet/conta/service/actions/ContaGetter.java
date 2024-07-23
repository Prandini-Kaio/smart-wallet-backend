package com.prandini.smartwallet.conta.service.actions;

/*
 * @author prandini
 * created 4/16/24
 */

import com.prandini.smartwallet.common.exception.CommonExceptionSupplier;
import com.prandini.smartwallet.common.model.AutcompleteDTO;
import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.conta.model.ContaInput;
import com.prandini.smartwallet.conta.repository.ContaRepository;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@CommonsLog
public class ContaGetter {

    @Resource
    private ContaRepository repository;

    public Page<Conta> getAll(Pageable pageable){
        log.info("Buscando todas as contas.");

        return repository.findAll(pageable);
    }

    public Conta getContaByFilter(String filter){
        log.info(String.format("Buscando conta por filtro %s.", filter));

        return repository.getContaByFilter(filter).orElseThrow(CommonExceptionSupplier.naoEncontrado("Conta"));
    }

    public boolean existsContaByNomeBanco(ContaInput input) {
        return repository.existsContaByNomeBanco(input.getNome(), input.getBanco());
    }

    public List<AutcompleteDTO> autocompleteContas(String conta) {
        return this.repository.autcompleteContas(conta);
    }
}
