package com.prandini.smartwallet.cartao.repository;

import com.prandini.smartwallet.cartao.domain.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author kaiooliveira
 * created 05/05/2025
 */

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {
}
