package com.prandini.smartwallet.conta.repository;

import com.prandini.smartwallet.common.model.TotalizadorFinanceiro;
import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.conta.model.ContaFilter;

import java.util.List;

public interface ContaRepositoryCustom {

    List<Conta> byFilter(ContaFilter filter);

    TotalizadorFinanceiro totalizadorByFilter(ContaFilter filter);
}
