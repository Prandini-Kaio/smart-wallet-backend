package com.prandini.smartwallet.conta.repository;

import com.prandini.smartwallet.common.model.TotalizadorFinanceiro;
import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.conta.model.ContaFilter;

import java.util.List;
import java.util.Optional;

public interface ContaRepositoryCustom {

    List<Conta> byFilter(ContaFilter filter);

    Optional<Conta> optionalByFilter(ContaFilter filter);

    TotalizadorFinanceiro totalizadorByFilter(ContaFilter filter);
}
