package com.prandini.smartwallet.lancamento.repository;


import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.lancamento.domain.StatusLancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryCustom {

    @Query("SELECT l FROM Lancamento l WHERE MONTH(l.dtCriacao) = :mes")
    Optional<List<Lancamento>> findByDtCriacao(Integer mes);

    @Query("SELECT l " +
            "FROM Lancamento l ")
    List<Lancamento> findTodos();

    @Query("SELECT l " +
            "FROM Lancamento l " +
            "WHERE l.status = :status ")
    List<Lancamento> findByStatus(StatusLancamento status);

    @Query("SELECT l " +
            "FROM Lancamento l " +
            "WHERE l.conta.id = :idConta")
    List<Lancamento> getByConta(Long idConta);

    @Query("SELECT l " +
            "FROM Lancamento l " +
            "JOIN l.conta c " +
            " WHERE UPPER(c.nome) LIKE CONCAT('%', UPPER(:filter), '%') " +
            " OR UPPER(c.banco) LIKE CONCAT('%', UPPER(:filter), '%') ")
    Optional<List<Lancamento>> getByConta(String filter);

}
