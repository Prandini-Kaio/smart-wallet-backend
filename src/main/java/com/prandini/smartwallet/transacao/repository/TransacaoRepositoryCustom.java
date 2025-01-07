package com.prandini.smartwallet.transacao.repository;


import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.model.TransacaoFilter;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public interface TransacaoRepositoryCustom {



    List<Transacao> getTransacoesByFilter(TransacaoFilter filter);

    List<Transacao> byVencimentoConta(Conta conta, Month mes);
}
