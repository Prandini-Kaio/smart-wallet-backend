package com.prandini.smartwallet.transacao.repository;

import com.prandini.smartwallet.transacao.domain.Parcela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author kaiooliveira
 * created 05/05/2025
 */

@Repository
public interface ParcelaRepository extends JpaRepository<Parcela, Long> {
}
