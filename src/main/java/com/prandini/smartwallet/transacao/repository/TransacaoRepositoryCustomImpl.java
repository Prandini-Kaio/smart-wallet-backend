package com.prandini.smartwallet.transacao.repository;

import com.prandini.smartwallet.transacao.domain.Transacao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

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

//    @Override
//    public List<Transacao> findByFilter(TransacaoFilter filter) {
//        StringBuilder sb = new StringBuilder();
//
//        Map<String, Object> params = new HashMap<>();
//
//        // Query
//        sb.append("SELECT t FROM Transacao t ")
//                .append("WHERE 1=1 ");
//
//        // Setando os parametros da query, caso o filtro nao seja nulo
//        Optional.ofNullable(filter).ifPresent(f -> buildParams(params, sb, f));
//
//        // Criando a query com base no StringBuilder
//        Query query = this.entityManager.createQuery(sb.toString());
//
//        params.forEach(query::setParameter);
//
//        return query.getResultList();
//    }

//    private void buildParams(Map<String, Object> params, StringBuilder sb, TransacaoFilter filter){
//        safeAddParams(params, "mes", filter.getMes(), sb, " AND MONTH(t.dtVencimento) = :mes ");
//        safeAddParams(params, "status", filter.getStatus(), sb, " AND t.status = :status ");
//    }

    private static void safeAddParams(Map<String, Object> params, String name, Object value, StringBuilder sb, String queryPart){
        if(value != null){
            params.put(name, value);
            sb.append(queryPart);
        }
    }
}
