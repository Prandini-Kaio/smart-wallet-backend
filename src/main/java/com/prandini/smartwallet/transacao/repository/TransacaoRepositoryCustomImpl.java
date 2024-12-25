package com.prandini.smartwallet.transacao.repository;

import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.model.TransacaoFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
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
    public List<Transacao> getTransacoesByFilter(TransacaoFilter filter) {
        StringBuilder sb = new StringBuilder();

        Map<String, Object> params = new HashMap<>();

        // Query
        sb.append("SELECT t FROM Transacao t ")
                .append(" JOIN t.lancamento l ")
                .append(" JOIN l.conta c ")
                .append("WHERE 1=1 ");

        Optional.ofNullable(filter).ifPresent(f -> buildParams(params, sb, f));

        sb.append(" ORDER BY t.dtVencimento DESC, l.dtCriacao DESC ");

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

    private void buildParams(Map<String, Object> params, StringBuilder sb, TransacaoFilter filter){
        safeAddParams(params, "id", filter.getId(), sb, " AND l.id = :id ");
        safeAddParams(params, "idLancamento", filter.getIdLancamento(), sb, " AND l.id = :idLancamento ");
        safeAddParams(params, "tipo", filter.getTipo(), sb, " AND l.tipoLancamento = :tipo ");
        safeAddParams(params, "categoria", filter.getCategoria(), sb, " AND l.categoriaLancamento = :categoria ");
        safeAddParams(params, "pagamento", filter.getPagamento(), sb, " AND l.tipoPagamento = :pagamento ");
        safeAddParams(params, "status", filter.getStatus(), sb, " AND t.status = :status ");
        safeAddParams(params, "dtInicio", filter.getDtInicio(), sb, " AND t.dtVencimento >= :dtInicio ");
        safeAddParams(params, "dtFim", filter.getDtFim(), sb, " AND t.dtVencimento <= :dtFim ");
        safeAddParams(params, "banco", filter.getBancoConta(), sb, " AND LOWER(c.banco) LIKE CONCAT('%', LOWER(:banco), '%') ");
        safeAddParams(params, "nome", filter.getNomeConta(), sb, " AND LOWER(c.nome) LIKE CONCAT('%', LOWER(:nome), '%') ");

    }
}
