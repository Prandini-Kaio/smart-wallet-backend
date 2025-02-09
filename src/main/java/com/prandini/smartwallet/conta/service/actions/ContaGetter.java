package com.prandini.smartwallet.conta.service.actions;

/*
 * @author prandini
 * created 4/16/24
 */

import com.prandini.smartwallet.common.exception.CommonExceptionSupplier;
import com.prandini.smartwallet.common.model.TotalizadorFinanceiro;
import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.conta.model.ContaFilter;
import com.prandini.smartwallet.conta.model.ContaInput;
import com.prandini.smartwallet.conta.repository.ContaRepository;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@CommonsLog
public class ContaGetter {

    @Resource
    private ContaRepository repository;

    public List<Conta> byFilter(ContaFilter filter){
        log.info(String.format("Buscando contas por filtro %s.", filter));
        return repository.byFilter(filter);
    }

    public Conta findByFilter(ContaFilter filter){
        log.info(String.format("Buscando contas por filtro %s.", filter));
        return repository.optionalByFilter(filter).orElseThrow(CommonExceptionSupplier.naoEncontrado("Conta", filter.getNome()));
    }

    public boolean existsContaByNomeBanco(ContaInput input) {
        return repository.existsContaByNomeBanco(input.getNome(), input.getBanco());
    }

    public Conta byId(Long id) {
        log.info(String.format("Buscando conta por id %s.", id));
        return repository.findById(id).orElseThrow(CommonExceptionSupplier.naoEncontrado("Conta"));
    }

    public TotalizadorFinanceiro getTotalizadorByFilter(ContaFilter filter){
        return this.repository.totalizadorByFilter(filter);
    }

    public List<Conta> findAll() {
        return this.repository.findAll();
    }
}
