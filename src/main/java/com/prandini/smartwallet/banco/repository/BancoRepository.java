package com.prandini.smartwallet.banco.repository;

import com.prandini.smartwallet.banco.domain.Banco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author kaiooliveira
 * created 15/05/2025
 */

@Repository
public interface BancoRepository extends JpaRepository<Banco, Long> {
}
