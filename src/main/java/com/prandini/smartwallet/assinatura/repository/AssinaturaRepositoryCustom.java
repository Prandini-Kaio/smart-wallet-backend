package com.prandini.smartwallet.assinatura.repository;

import com.prandini.smartwallet.assinatura.domain.Assinatura;
import com.prandini.smartwallet.assinatura.model.AssinaturaFilter;

import java.util.List;
import java.util.stream.Stream;

public interface AssinaturaRepositoryCustom {

    List<Assinatura> streamByFilter(AssinaturaFilter filter);
}
