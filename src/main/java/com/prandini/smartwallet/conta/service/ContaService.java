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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ContaService {

    @Resource
    private ContaCreator creator;

    @Resource
    private ContaGetter getter;

    @Resource
    private ContaValidator validator;

    public Page<ContaOutput> getAll(Pageable pageable){
        return getter.getAll(pageable).map(ContaConverter::toOutput);
    }

    public ContaOutput create(ContaInput input){
//        validator.validarCriacao(input);
        return ContaConverter.toOutput(creator.criarConta(input));
    }
}
