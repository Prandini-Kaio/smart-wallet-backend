package com.prandini.smartwallet.assinatura.repository;

import com.prandini.smartwallet.assinatura.domain.Assinatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author kaiooliveira
 * created 04/09/2024
 */

@Repository
public interface AssinaturaRepository extends JpaRepository<Assinatura, Long> {
}
