package com.prandini.smartwallet.lancamento.repository;

import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.lancamento.model.LancamentoFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

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
                .append("JOIN l.contaDestino cd ")
                .append("LEFT JOIN l.contaOrigem co ")
                .append("WHERE 1=1 ");

        Optional.ofNullable(filter).ifPresent(f -> buildParams(params, sb, f));

        sb.append(" ORDER BY l.dtCriacao DESC ");

        Query query = this.entityManager.createQuery(sb.toString());

        params.forEach(query::setParameter);

        return query.getResultList();
    }

    private void buildParams(Map<String, Object> params, StringBuilder sb, LancamentoFilter filter){
        safeAddParams(params, "tipo", filter.getTipo(), sb, " AND l.tipoLancamento = :tipo ");

        safeAddParams(params, "pagamento", filter.getPagamento(), sb, " AND l.tipoPagamento = :pagamento ");

        if (filter.getDtInicio() != null && filter.getDtFim() != null) {
            sb.append(" AND EXISTS (")
                    .append(" SELECT 1 FROM Transacao t2 ")
                    .append(" WHERE t2.lancamento = l ")
                    .append(" AND t2.dtVencimento BETWEEN :dtInicioVencimento AND :dtFimVencimento ")
                    .append(")");
            params.put("dtInicioVencimento", filter.getDtInicio());
            params.put("dtFimVencimento", filter.getDtFim());
        }

        if(filter.getCategorias() != null && !filter.getCategorias().isEmpty()){
            safeAddParams(params, "categoria", filter.getCategorias(), sb, " AND l.categoriaLancamento IN :categoria ");
        }

        if(filter.getStatus() != null && !filter.getStatus().isEmpty()){
            safeAddParams(params, "status", filter.getStatus(), sb, " AND l.status IN :status ");
        }

        if(filter.getContaDestinoIds() != null && !filter.getContaDestinoIds().isEmpty() && filter.getContaDestinoIds().get(0) != 0){
            safeAddParams(params, "contaDestinoIds", filter.getContaDestinoIds(), sb, " AND cd.id IN :contaDestinoIds ");
        }

        if(filter.getContaOrigemIds() != null && !filter.getContaOrigemIds().isEmpty() && filter.getContaOrigemIds().get(0) != 0){
            safeAddParams(params, "contaOrigemIds", filter.getContaOrigemIds(), sb, " AND co.id IN :contaOrigemIds ");
        }
    }

    private static void safeAddParams(Map<String, Object> params, String name, Object value, StringBuilder sb, String queryPart){
        if(value != null){
            params.put(name, value);
            sb.append(queryPart);
        }
    }
}
