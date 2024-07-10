package com.prandini.smartwallet.lancamento.repository;


import com.prandini.smartwallet.lancamento.domain.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryCustom {

    @Query("SELECT l FROM Lancamento l WHERE MONTH(l.dtCriacao) = :mes")
    Optional<List<Lancamento>> findByDtCriacao(Integer mes);
}
