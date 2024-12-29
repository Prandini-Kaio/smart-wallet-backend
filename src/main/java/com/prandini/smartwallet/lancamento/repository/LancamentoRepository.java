package com.prandini.smartwallet.lancamento.repository;


import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.lancamento.domain.StatusLancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryCustom {


    @Query("SELECT l " +
            "FROM Lancamento l " +
            " ORDER BY l.dtCriacao DESC ")
    List<Lancamento> findTodos();

    @Query("SELECT l " +
            "FROM Lancamento l " +
            "WHERE l.conta.id = :idConta")
    List<Lancamento> getByConta(Long idConta);


    @Query(" DELETE FROM Lancamento l " +
            " WHERE l.conta.id = :id ")
    @Modifying
    void deleteByConta(Long id);
}
