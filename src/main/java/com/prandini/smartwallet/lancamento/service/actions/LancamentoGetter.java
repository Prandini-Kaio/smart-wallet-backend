package com.prandini.smartwallet.lancamento.service.actions;

/*
 * @author prandini
 * created 5/10/24
 */

import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.lancamento.domain.dto.LancamentoFilter;
import com.prandini.smartwallet.lancamento.repository.LancamentoRepository;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class LancamentoGetter {

    @Resource
    private LancamentoRepository repository;

    public Page<Lancamento> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<Lancamento> getListAll(){
        return repository.findAll();
    }

    public List<Lancamento> findByDtCriacao(Integer mes) {
        return repository.findByDtCriacao(mes);
    }

    public List<Lancamento> findByFilter(LancamentoFilter filter) {
        return repository.findByFilter(filter);
    }
}
