package com.prandini.smartwallet.transacao.repository;


import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.model.TransacaoFilter;

import java.time.LocalDate;
import java.util.List;

public interface TransacaoRepositoryCustom {

    List<Transacao> getByPeriodo(String conta, LocalDate dtInicio, LocalDate dtFim);

    List<Transacao> getTransacoesByFilter(TransacaoFilter filter);
}
