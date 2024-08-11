package com.prandini.smartwallet.lancamento.repository;


import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.lancamento.model.LancamentoFilter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface LancamentoRepositoryCustom {

    List<Lancamento> findByFilter(LancamentoFilter filter);

    List<Lancamento> getByPeriodo(String conta, LocalDate dtInicio, LocalDate dtFim);
}
