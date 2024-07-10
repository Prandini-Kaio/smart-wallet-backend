package com.prandini.smartwallet.conta.service;

/*
 * @author prandini
 * created 4/5/24
 */

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

@Service
@CommonsLog
public class ContaService {

    @Resource
    private ContaCreator creator;

    @Resource
    private ContaGetter getter;

    public Page<ContaOutput> getAll(Pageable pageable){
        log.info("Iniciando consulta de contas.");

        return getter.getAll(pageable).map(ContaConverter::toOutput);
    }

    public ContaOutput create(ContaInput input){
        log.info(String.format("Iniciando criação de conta %s.", input.getNome()));

        return ContaConverter.toOutput(creator.criarConta(input));
    }
}
