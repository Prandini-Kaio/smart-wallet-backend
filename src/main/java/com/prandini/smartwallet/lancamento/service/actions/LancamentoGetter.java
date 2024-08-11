package com.prandini.smartwallet.lancamento.service.actions;

/*
 * @author prandini
 * created 5/10/24
 */

import com.prandini.smartwallet.common.exception.CommonExceptionSupplier;
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

    public Page<Lancamento> getAll(Pageable pageable) {
        log.info("Consultando todos os lançamentos");

        return repository.findAll(pageable);
    }

    public Lancamento byId(Long id){
        return repository.findById(id).orElseThrow(CommonExceptionSupplier.naoEncontrado("Lançamento"));
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

    public List<String> getCategorias() {
        return Arrays.stream(CategoriaLancamentoEnum.values()).map(c -> c.nome).toList();
    }

    public TotalizadorFinanceiro getTotalizador(String conta) {
        log.info("Calculando totalizador financeiro da conta");

        List<Lancamento> lancamentos = this.getByConta(conta);

        return TotalizadorFinanceiro.calcularTotalizador(lancamentos);
    }

    public TotalizadorFinanceiro getTotalizadorByPeriodo(String conta, LocalDate dtInicio, LocalDate dtFim){
        log.info("Calculando totalizador financeiro da conta");

        List<Lancamento> lancamentos = repository.getByPeriodo(conta, dtInicio, dtFim);

        return TotalizadorFinanceiro.calcularTotalizador(lancamentos);
    }

    public List<Lancamento> getByConta(Long id) {
        log.info(String.format("Consultando conta pelo id %s", id));

        return this.repository.getByConta(id);
    }

    public List<Lancamento> getByConta(String filter) {
        log.info(String.format("Consultando conta pelo filtro %s", filter));

        List<Lancamento> lancamentos = this.repository.getByConta(filter).orElseThrow(CommonExceptionSupplier.naoEncontrado("Lançamento"));

        return lancamentos;
    }
}
