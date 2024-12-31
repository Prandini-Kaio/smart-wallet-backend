package com.prandini.smartwallet.orcamento.repository;

import com.prandini.smartwallet.lancamento.domain.CategoriaLancamentoEnum;
import com.prandini.smartwallet.orcamento.domain.Orcamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Month;
import java.util.Optional;

/**
 * @author kaiooliveira
 * created 27/12/2024
 */
public interface OrcamentoRepository extends JpaRepository<Orcamento, Long> {


    @Query("SELECT o " +
            "FROM Orcamento o " +
            "WHERE o.categoria = :categoria " +
            "AND o.mes = :mes ")
    Optional<Orcamento> findByCategoriaAndMes(CategoriaLancamentoEnum categoria, Month mes);
}
