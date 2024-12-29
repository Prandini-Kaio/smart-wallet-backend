package com.prandini.smartwallet.orcamento.repository;

import com.prandini.smartwallet.orcamento.domain.Orcamento;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author kaiooliveira
 * created 27/12/2024
 */
public interface OrcamentoRepository extends JpaRepository<Orcamento, Long> {

}
