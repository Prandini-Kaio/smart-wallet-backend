package com.prandini.smartwallet.assinatura.repository;

import com.prandini.smartwallet.assinatura.domain.Assinatura;
import com.prandini.smartwallet.assinatura.model.AssinaturaFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        sb.append("SELECT a FROM Assinatura a ")
                .append(" JOIN a.contaDestino cd ")
                .append(" LEFT JOIN a.contaOrigem co ")
                .append("WHERE 1=1 ");

        safeAddParams(params, "contasDestino", filter.getContaDestinoIds(), sb, " AND cd.id IN :contasDestino ");
        safeAddParams(params, "contasOrigem", filter.getContaDestinoIds(), sb, " AND co.id IN :contasOrigem ");
        safeAddParams(params, "valor", filter.getValor(), sb, " AND a.valor = :valor ");
        safeAddParams(params, "dtInicio", filter.getDtInicio(), sb, " AND a.dtInicio >= :dtInicio ");
        safeAddParams(params, "dtFim", filter.getDtFim(), sb, " AND a.dtFim <= :dtFim ");
        safeAddParams(params, "ativa", filter.getAtiva(), sb, " AND a.ativa = :ativa ");

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
