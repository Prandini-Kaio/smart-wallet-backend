package com.prandini.smartwallet.cartao.repository;

import com.prandini.smartwallet.cartao.domain.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author kaiooliveira
 * created 05/05/2025
 */

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {

    @Query(" SELECT c FROM Cartao c " +
            " JOIN c.banco b " +
            " WHERE b.id = :bancoId ")
    List<Cartao> findAllByBancoId(Long bancoId);
}
