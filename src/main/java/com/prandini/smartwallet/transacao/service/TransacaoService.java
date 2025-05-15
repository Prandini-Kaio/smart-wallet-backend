package com.prandini.smartwallet.transacao.service;

import com.prandini.smartwallet.transacao.converter.TransacaoConverter;
import com.prandini.smartwallet.transacao.model.TransacaoInput;
import com.prandini.smartwallet.transacao.model.TransacaoOutput;
import com.prandini.smartwallet.transacao.repository.TransacaoRepository;
import com.prandini.smartwallet.transacao.service.actions.TransacaoCreator;
import com.prandini.smartwallet.transacao.service.actions.TransacaoGetter;
import com.prandini.smartwallet.transacao.service.actions.TransacaoUpdater;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
 * @author prandini
 * created 4/17/24
 */

@Service
@CommonsLog
public class TransacaoService {

    @Resource
    private TransacaoGetter getter;

    @Resource
    private TransacaoCreator creator;

    @Resource
    private TransacaoUpdater updater;

    @Resource
    private TransacaoConverter converter;

    @Resource
    private TransacaoRepository repository;

    @Transactional
    public TransacaoOutput createWithTransactional(TransacaoInput input){
        return converter.toOutput(creator.fromInput(input));
    }

    public List<TransacaoOutput> findAll() {
        return converter.toListOutputs(repository.findAll());
    }
}
