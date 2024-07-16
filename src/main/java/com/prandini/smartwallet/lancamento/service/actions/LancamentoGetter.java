package com.prandini.smartwallet.lancamento.service.actions;

/*
 * @author prandini
 * created 5/10/24
 */

import com.prandini.smartwallet.common.exception.CommonExceptionSupplier;
import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.lancamento.domain.dto.LancamentoFilter;
import com.prandini.smartwallet.lancamento.repository.LancamentoRepository;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@CommonsLog
public class LancamentoGetter {

    @Resource
    private LancamentoRepository repository;

    public Page<Lancamento> getAll(Pageable pageable) {
        log.info("Consultando todos os lançamentos");

        return repository.findAll(pageable);
    }

    public List<Lancamento> findTodos() {
        log.info("Consultando todos os lançamentos");

        return repository.findTodos();
    }

    public List<Lancamento> findByDtCriacao(Integer mes) {
        return repository.findByDtCriacao(mes)
                .orElseThrow(CommonExceptionSupplier.naoEncontrado("Lançamento"));
    }

    public List<Lancamento> findByFilter(LancamentoFilter filter) {
        return repository.findByFilter(filter);
    }
}
