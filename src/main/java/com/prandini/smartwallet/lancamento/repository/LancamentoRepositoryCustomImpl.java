package com.prandini.smartwallet.lancamento.repository;

import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.lancamento.model.LancamentoFilter;
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
 * created 4/29/24
 */
public class LancamentoRepositoryCustomImpl implements LancamentoRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Lancamento> findByFilter(LancamentoFilter filter) {
        StringBuilder sb = new StringBuilder();

        Map<String, Object> params = new HashMap<>();

        // Query
        sb.append("SELECT l FROM Lancamento l ")
                .append("WHERE 1=1 ");

        // Setando os parametros da query, caso o filtro nao seja nulo
        Optional.ofNullable(filter).ifPresent(f -> buildParams(params, sb, f));

        // Criando a query com base no StringBuilder
        Query query = this.entityManager.createQuery(sb.toString());

        params.forEach(query::setParameter);

        return query.getResultList();
    }

    @Override
    public List<Lancamento> getByPeriodo(String conta, LocalDate dtInicio, LocalDate dtFim) {
        StringBuilder sb = new StringBuilder();

        Map<String, Object> params = new HashMap<>();

        // Query
        sb.append("SELECT l FROM Lancamento l ")
                .append(" JOIN l.conta c ")
                .append("WHERE 1=1 ")
                .append(" AND (c.tipoConta <> TipoConta.INVESTIMENTO AND c.tipoConta <> TipoConta.ECONOMIA) ");

        // Setando os parametros da query, caso o filtro nao seja nulo
        Optional.ofNullable(conta).ifPresent(c -> safeAddParams(params, "conta", conta, sb, " AND (UPPER(c.nome) LIKE CONCAT('%', UPPER(:conta), '%') OR UPPER(c.banco) LIKE CONCAT('%', UPPER(:conta), '%'))"));
        Optional.ofNullable(conta).ifPresent(c -> safeAddParams(params, "conta", conta, sb, " AND UPPER(c.nome) LIKE CONCAT('%', UPPER(:conta), '%') "));
        Optional.ofNullable(dtInicio).ifPresent(dt -> safeAddParams(params, "dtInicio", dtInicio.atTime(0, 0, 0), sb, " AND l.dtInicio >= :dtInicio "));
        Optional.ofNullable(dtFim).ifPresent(dt -> safeAddParams(params, "dtFim", dtFim.atTime(23,59, 59), sb, " AND l.dtFim <= dtFim "));

        // Criando a query com base no StringBuilder
        Query query = this.entityManager.createQuery(sb.toString());

        params.forEach(query::setParameter);

        return query.getResultList();
    }

    private void buildParams(Map<String, Object> params, StringBuilder sb, LancamentoFilter filter){
        safeAddParams(params, "mes", filter.getMes(), sb, " AND MONTH(l.dtCriacao) = :mes ");
        safeAddParams(params, "status", filter.getTipoLancamento(), sb, " AND l.tipoLancamento = :status ");
    }

    private static void safeAddParams(Map<String, Object> params, String name, Object value, StringBuilder sb, String queryPart){
        if(value != null){
            params.put(name, value);
            sb.append(queryPart);
        }
    }
}
