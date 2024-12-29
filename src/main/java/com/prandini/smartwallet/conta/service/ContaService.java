package com.prandini.smartwallet.conta.service;

/*
 * @author prandini
 * created 4/5/24
 */

import com.prandini.smartwallet.common.model.TotalizadorFinanceiro;
import com.prandini.smartwallet.conta.converter.ContaConverter;
import com.prandini.smartwallet.conta.model.ContaFilter;
import com.prandini.smartwallet.conta.model.ContaInput;
import com.prandini.smartwallet.conta.model.ContaOutput;
import com.prandini.smartwallet.conta.service.actions.ContaCreator;
import com.prandini.smartwallet.conta.service.actions.ContaDeleter;
import com.prandini.smartwallet.conta.service.actions.ContaGetter;
import com.prandini.smartwallet.conta.service.actions.ContaUpdater;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CommonsLog
public class ContaService {

    @Resource
    private ContaCreator creator;

    @Resource
    private ContaUpdater updater;

    @Resource
    private ContaGetter getter;

    @Resource
    private ContaDeleter deleter;

    @Resource
    private ContaConverter converter;

    public ContaOutput create(ContaInput input){
        log.info(String.format("Iniciando criação de conta %s.", input.getNome()));

        return converter.toOutput(creator.criarConta(input));
    }

    public TotalizadorFinanceiro getTotalizadorByFilter(ContaFilter filter) {
        return getter.getTotalizadorByFilter(filter);
    }

    public List<ContaOutput> getByFilter(ContaFilter filter) {
        return this.getter.byFilter(filter).stream().map(converter::toOutput).toList();
    }

    public ContaOutput update(@Valid ContaInput input) {
        log.info(String.format("Iniciando atualização de conta com id %s", input.getId()));
        return converter.toOutput(this.updater.atualizar(input));
    }

    public void deletar(Long id) {
        log.info(String.format("Iniciando deleção de conta com id %s", id));
        this.deleter.deletar(id);
    }
}
