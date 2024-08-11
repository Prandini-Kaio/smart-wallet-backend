package com.prandini.smartwallet.transacao.repository;

import com.prandini.smartwallet.transacao.domain.Transacao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/*
 * @author prandini
 * created 5/4/24
 */
public class TransacaoRepositoryCustomImpl implements TransacaoRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Transacao> findByPeriodo(String conta, LocalDate dtInicio, LocalDate dtFim) {
        StringBuilder sb = new StringBuilder();

        Map<String, Object> params = new HashMap<>();

        // Query
        sb.append("SELECT t FROM Transacao t ")
                .append(" JOIN t.lancamento l ")
                .append(" JOIN l.conta c ")
                .append("WHERE 1=1 ");

        Optional.ofNullable(conta).ifPresent(c -> safeAddParams(params, "conta", conta, sb, " AND (UPPER(c.nome) LIKE CONCAT('%', UPPER(:conta), '%') OR UPPER(c.banco) LIKE CONCAT('%', UPPER(:conta), '%'))"));
        Optional.ofNullable(dtInicio).ifPresent(dt -> safeAddParams(params, "dtInicio", dtInicio.atTime(0, 0, 0), sb, " AND t.dtVencimento >= :dtInicio "));
        Optional.ofNullable(dtFim).ifPresent(dt -> safeAddParams(params, "dtFim", dtFim.atTime(23,59, 59), sb, " AND t.dtVencimento <= :dtFim "));

        // Criando a query com base no StringBuilder
        Query query = this.entityManager.createQuery(sb.toString());

        params.forEach(query::setParameter);

        return query.getResultList();
    }

    private static void safeAddParams(Map<String, Object> params, String name, Object value, StringBuilder sb, String queryPart){
        if(value != null){
            params.put(name, value);
            sb.append(queryPart);
        }
    }
}
