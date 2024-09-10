package com.prandini.smartwallet.transacao.repository;

import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.domain.StatusTransacaoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long>, TransacaoRepositoryCustom {

    @Query("SELECT t FROM Transacao t WHERE MONTH(t.dtVencimento) = :month")
    List<Transacao> findByVencimento(Integer month);

    @Query("SELECT t FROM Transacao t JOIN t.lancamento l JOIN l.conta c WHERE lower(c.banco) LIKE CONCAT('%', LOWER(:filter),'%') OR l.descricao LIKE CONCAT('%', LOWER(:filter), '%') OR c.nome LIKE CONCAT('%', LOWER(:filter), '%')")
    List<Transacao> findByStringFilter(String filter);

    @Query("SELECT t FROM Transacao t  WHERE t.status = :status AND MONTH(t.dtVencimento) = :month")
    List<Transacao> findByFilter(StatusTransacaoEnum status, Integer month);

    @Query(" SELECT t " +
            " FROM Transacao t " +
            " WHERE t.lancamento.id = :idLancamento " +
            " ORDER BY t.dtVencimento ASC ")
    List<Transacao> findByIdLancamento(Long idLancamento);

    @Query("SELECT COUNT(t) > 0 " +
            "FROM Transacao t " +
            "JOIN t.lancamento l " +
            "WHERE l.id = :idLancamento " +
            "AND t.dtVencimento < CURRENT_DATE")
    boolean hasTransacaoVencida(Long idLancamento);
}
