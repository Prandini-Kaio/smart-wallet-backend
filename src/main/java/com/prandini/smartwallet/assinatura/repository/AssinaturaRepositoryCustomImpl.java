package com.prandini.smartwallet.assinatura.repository;

import com.prandini.smartwallet.assinatura.domain.Assinatura;
import com.prandini.smartwallet.assinatura.model.AssinaturaFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/*
 * @author prandini
 * created 9/4/24
 */
public class AssinaturaRepositoryCustomImpl implements AssinaturaRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Assinatura> byFilter(AssinaturaFilter filter) {
        StringBuilder sb = new StringBuilder();

        Map<String, Object> params = new HashMap<>();

        // Query
        sb.append("SELECT a FROM Assinatura a ")
                .append(" JOIN a.conta c ")
                .append("WHERE 1=1 ");

        safeAddParams(params, "conta", filter.getConta().toLowerCase(), sb, " AND LOWER(c.banco) LIKE CONCAT('%', :conta, '%') OR LOWER(c.nome) LIKE CONCAT('%', :conta, '%') ");
        safeAddParams(params, "valor", filter.getValor(), sb, " AND a.valor = :valor ");
        safeAddParams(params, "dtInicio", filter.getDtInicio(), sb, " AND a.dtInicio >= :dtInicio ");
        safeAddParams(params, "dtFim", filter.getDtFim(), sb, " AND a.dtFim <= :dtFim ");
        safeAddParams(params, "desc", filter.getDescricao(), sb, " AND a.descricao <= :desc ");
        safeAddParams(params, "ativa", filter.isAtiva(), sb, " AND a.ativa = :ativa ");

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
