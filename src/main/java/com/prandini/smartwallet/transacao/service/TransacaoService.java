package com.prandini.smartwallet.transacao.service;

import com.prandini.smartwallet.transacao.converter.TransacaoConverter;
import com.prandini.smartwallet.transacao.domain.TransacaoFilter;
import com.prandini.smartwallet.transacao.domain.dto.TransacaoOutput;
import com.prandini.smartwallet.transacao.repository.TransacaoRepository;
import com.prandini.smartwallet.transacao.service.actions.TransacaoGetter;
import com.prandini.smartwallet.transacao.service.actions.TransacaoUpdater;
import com.prandini.smartwallet.transacao.service.actions.TransacaoValidator;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * @author prandini
 * created 4/17/24
 */
@Service
public class TransacaoService {

    @Resource
    private TransacaoGetter getter;

    @Resource
    private TransacaoUpdater updater;

    @Resource
    private TransacaoValidator validator;

    @Resource
    private TransacaoRepository repository;

    public TransacaoOutput pagarTransacao(Long id) {
        validator.validarPagament(id);
        return TransacaoConverter.toOutput(updater.pagarTransacao(id));
    }

    public Page<TransacaoOutput> findByMonth(Integer month){
        return new PageImpl<>(getter.findByMonth(month))
                .map(TransacaoConverter::toOutput);
    }

    public Page<TransacaoOutput> findByStringFilter(String filter){
        return new PageImpl<>(getter.findByStringFilter(filter))
                .map(TransacaoConverter::toOutput);
    }

    public List<TransacaoOutput> findByFilter(TransacaoFilter filter){
        return TransacaoConverter.toListOutputs(getter.findByFilter(filter));
    }


}
