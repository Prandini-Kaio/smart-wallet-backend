package com.prandini.smartwallet.lancamento.service;

/*
 * @author prandini
 * created 4/16/24
 */

import com.prandini.smartwallet.lancamento.converter.LancamentoConverter;
import com.prandini.smartwallet.lancamento.domain.dto.LancamentoFilter;
import com.prandini.smartwallet.lancamento.domain.dto.LancamentoInput;
import com.prandini.smartwallet.lancamento.domain.dto.LancamentoOutput;
import com.prandini.smartwallet.lancamento.service.actions.LancamentoCreator;
import com.prandini.smartwallet.lancamento.service.actions.LancamentoGetter;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LancamentoService {

    @Resource
    private LancamentoCreator creator;

    @Resource
    private LancamentoGetter getter;


    public Page<LancamentoOutput> findAll(Pageable pageable){
        return getter.getAll(pageable).map(LancamentoConverter::toOutput);
    }

    public LancamentoOutput criarLancamento(LancamentoInput input) {

        return LancamentoConverter.toOutput(creator.criarLancamento(input));
    }

    public List<LancamentoOutput> findByVencimento(Integer mes) {
        return getter.findByDtCriacao(mes).stream().map(LancamentoConverter::toOutput).toList();
    }

    public List<LancamentoOutput> findByFilter(LancamentoFilter filter) {
        return getter.findByFilter(filter).stream().map(LancamentoConverter::toOutput).toList();
    }
}
