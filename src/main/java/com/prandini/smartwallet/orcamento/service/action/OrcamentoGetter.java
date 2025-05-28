package com.prandini.smartwallet.orcamento.service.action;

import com.prandini.smartwallet.common.exception.CommonExceptionSupplier;
import com.prandini.smartwallet.lancamento.domain.CategoriaLancamentoEnum;
import com.prandini.smartwallet.orcamento.domain.Orcamento;
import com.prandini.smartwallet.orcamento.repository.OrcamentoRepository;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kaiooliveira
 * created 27/12/2024
 */

@Component
@CommonsLog
public class OrcamentoGetter {

    @Resource
    private OrcamentoRepository repository;

    public List<Orcamento> listarOrcamentos() {
        log.info("Buscando orcamentos cadastrados.");

        List<Orcamento> todos = this.repository.findAll();
        return todos.stream().filter(orcamento -> orcamento.getMes().compareTo(LocalDate.now().getMonth()) == 0).collect(Collectors.toList());
    }

    public Orcamento byId(Long id) {
        log.info("Buscando orçamento por id.");
        return repository.findById(id).orElseThrow(CommonExceptionSupplier.naoEncontrado("Orcamento", id.toString()));
    }

    public boolean existsByCategoriaMes(CategoriaLancamentoEnum categoria, Month mes){
        return this.repository.findByCategoriaAndMes(categoria, mes).isPresent();
    }
}
