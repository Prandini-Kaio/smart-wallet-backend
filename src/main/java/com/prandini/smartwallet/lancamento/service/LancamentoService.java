package com.prandini.smartwallet.lancamento.service;

/*
 * @author prandini
 * created 4/16/24
 */

import com.prandini.smartwallet.lancamento.converter.LancamentoConverter;
import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.lancamento.domain.dto.LancamentoFilter;
import com.prandini.smartwallet.lancamento.domain.dto.LancamentoInput;
import com.prandini.smartwallet.lancamento.domain.dto.LancamentoOutput;
import com.prandini.smartwallet.lancamento.service.actions.LancamentoCreator;
import com.prandini.smartwallet.lancamento.service.actions.LancamentoGetter;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@CommonsLog
public class LancamentoService {

    @Resource
    private LancamentoCreator creator;

    @Resource
    private LancamentoGetter getter;


    public List<LancamentoOutput> findAll(Pageable pageable){
        log.info("Iniciando busca de todos os lancamentos.");

        List<Lancamento> lancamentos = getter.findTodos();

        return lancamentos.stream().map(LancamentoConverter::toOutput).toList();
    }

    @Transactional
    public LancamentoOutput criarLancamento(LancamentoInput input) {
        log.info("Iniciando criação de lancamento.");

        return LancamentoConverter.toOutput(creator.create(input));
    }

    public List<LancamentoOutput> findByVencimento(Integer mes) {
        log.info(String.format("Iniciando busca de lancamentos por mês %s.", mes ));

        return getter.findByDtCriacao(mes).stream().map(LancamentoConverter::toOutput).toList();
    }

    public List<LancamentoOutput> findByFilter(LancamentoFilter filter) {
        log.info(String.format("Iniciando busca de lancamentos por filtro %s.", filter));

        return getter.findByFilter(filter).stream().map(LancamentoConverter::toOutput).toList();
    }
}
