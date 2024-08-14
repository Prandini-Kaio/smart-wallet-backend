package com.prandini.smartwallet.conta.service;

/*
 * @author prandini
 * created 4/5/24
 */

import com.prandini.smartwallet.common.model.AutcompleteDTO;
import com.prandini.smartwallet.common.model.TotalizadorFinanceiro;
import com.prandini.smartwallet.conta.converter.ContaConverter;
import com.prandini.smartwallet.conta.model.ContaInput;
import com.prandini.smartwallet.conta.model.ContaOutput;
import com.prandini.smartwallet.conta.service.actions.ContaCreator;
import com.prandini.smartwallet.conta.service.actions.ContaGetter;
import com.prandini.smartwallet.conta.service.actions.ContaValidator;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CommonsLog
public class ContaService {

    @Resource
    private ContaCreator creator;

    @Resource
    private ContaGetter getter;

    @Resource
    private ContaConverter converter;

    public List<ContaOutput> getAll(){
        log.info("Iniciando consulta de contas.");

        return getter.getAll().stream().map(converter::toOutput).toList();
    }

    public ContaOutput create(ContaInput input){
        log.info(String.format("Iniciando criação de conta %s.", input.getNome()));

        return converter.toOutput(creator.criarConta(input));
    }

    public List<AutcompleteDTO> autcompleteContas(String conta) {
        return this.getter.autocompleteContas(conta);
    }

    public ContaOutput byId(Long id) {
        return converter.toOutput(getter.byid(id));
    }

    public TotalizadorFinanceiro getTotalizadorByFilter(String filter) {
        return getter.getTotalizadorByFilter(filter);
    }
}
