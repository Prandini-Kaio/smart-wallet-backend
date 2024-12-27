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
                .append("JOIN l.conta c ")
                .append("WHERE 1=1 ");

        Optional.ofNullable(filter).ifPresent(f -> buildParams(params, sb, f));

        sb.append(" ORDER BY l.dtCriacao DESC ");

        Query query = this.entityManager.createQuery(sb.toString());

        params.forEach(query::setParameter);

        return query.getResultList();
    }

    private void buildParams(Map<String, Object> params, StringBuilder sb, LancamentoFilter filter){
        safeAddParams(params, "tipo", filter.getTipo(), sb, " AND l.tipoLancamento = :tipo ");
        safeAddParams(params, "categoria", filter.getCategoria(), sb, " AND l.categoriaLancamento = :categoria ");
        safeAddParams(params, "pagamento", filter.getPagamento(), sb, " AND l.tipoPagamento = :pagamento ");
        safeAddParams(params, "status", filter.getStatus(), sb, " AND l.status = :status ");
        safeAddParams(params, "dtInicio", filter.getDtInicio(), sb, " AND l.dtCriacao >= :dtInicio ");
        safeAddParams(params, "dtFim", filter.getDtFim(), sb, " AND l.dtCriacao <= :dtFim ");
        safeAddParams(params, "banco", filter.getBancoConta(), sb, " AND LOWER(c.banco) LIKE CONCAT('%', LOWER(:banco), '%') ");
        safeAddParams(params, "nome", filter.getNomeConta(), sb, " AND LOWER(c.nome) LIKE CONCAT('%', LOWER(:nome), '%') ");
        safeAddParams(params, "contaIds", filter.getContaIds(), sb, " AND c.id IN :contaIds ");
    }

    private static void safeAddParams(Map<String, Object> params, String name, Object value, StringBuilder sb, String queryPart){
        if(value != null){
            params.put(name, value);
            sb.append(queryPart);
        }
    }
}
