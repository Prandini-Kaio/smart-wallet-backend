package com.prandini.smartwallet.conta.service.actions;

/*
 * @author prandini
 * created 4/16/24
 */

import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.conta.repository.ContaRepository;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ContaGetter {

    @Resource
    private ContaRepository repository;

    public Page<Conta> getAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Conta getContaByFilter(String filter){
        return repository.getContaByFilter(filter);
    }
}
