package com.prandini.smartwallet.lancamento.repository;


import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.lancamento.domain.dto.LancamentoFilter;

import java.util.List;

public interface LancamentoRepositoryCustom {

    List<Lancamento> findByFilter(LancamentoFilter filter);
}
