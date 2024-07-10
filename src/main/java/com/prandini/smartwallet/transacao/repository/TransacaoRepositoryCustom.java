package com.prandini.smartwallet.transacao.repository;


import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.domain.TransacaoFilter;

import java.util.List;

public interface TransacaoRepositoryCustom {

    List<Transacao> findByFilter(TransacaoFilter filter);
}
