package com.prandini.smartwallet.transacao.repository;


import com.prandini.smartwallet.transacao.domain.Transacao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TransacaoRepositoryCustom {

    List<Transacao> findByPeriodo(String conta, LocalDate dtInicio, LocalDate dtFim);
}
