package com.prandini.smartwallet.assinatura.repository;

import com.prandini.smartwallet.assinatura.domain.Assinatura;
import com.prandini.smartwallet.assinatura.model.AssinaturaFilter;

import java.util.List;

public interface AssinaturaRepositoryCustom {

    List<Assinatura> byFilter(AssinaturaFilter filter);
}
