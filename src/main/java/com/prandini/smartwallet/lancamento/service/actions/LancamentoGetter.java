package com.prandini.smartwallet.lancamento.service.actions;

/*
 * @author prandini
 * created 5/10/24
 */

import com.prandini.smartwallet.common.exception.CommonExceptionSupplier;
import com.prandini.smartwallet.common.model.ResumoFinanceiro;
import com.prandini.smartwallet.lancamento.domain.*;
import com.prandini.smartwallet.lancamento.model.LancamentoFilter;
import com.prandini.smartwallet.common.model.TotalizadorFinanceiro;
import com.prandini.smartwallet.lancamento.repository.LancamentoRepository;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@CommonsLog
public class LancamentoGetter {

    @Resource
    private LancamentoRepository repository;

    public Lancamento byId(Long id){
        return repository.findById(id).orElseThrow(CommonExceptionSupplier.naoEncontrado("Lançamento"));
    }

    public List<Lancamento> findTodos() {
        log.info("Consultando todos os lançamentos");

        return repository.findTodos();
    }

    public List<Lancamento> findByFilter(LancamentoFilter filter) {
        return repository.findByFilter(filter);
    }

    public TotalizadorFinanceiro getTotalizador(LancamentoFilter filter) {
        log.info("Calculando totalizador financeiro da conta");

        List<Lancamento> lancamentos = this.getByFilter(filter);

        return TotalizadorFinanceiro.calcularTotalizador(lancamentos);
    }

    private List<Lancamento> getByFilter(LancamentoFilter filter) {
        return this.repository.findByFilter(filter);
    }

    public List<Lancamento> getByConta(Long id) {
        log.info(String.format("Consultando conta pelo id %s", id));

        return this.repository.getByConta(id);
    }

    public ResumoFinanceiro getResumo(LancamentoFilter filter) {
        log.info("Calculando resumo financeiro da conta");

        List<Lancamento> lancamentos = this.getByFilter(filter);

        return ResumoFinanceiro.calcularResumo(lancamentos);
    }
}
