package com.prandini.smartwallet.transacao.repository;

import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.model.TransacaoFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
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

    @Override
    public List<Transacao> byVencimentoConta(Conta conta, Month mes) {
        StringBuilder sb = new StringBuilder();

        Map<String, Object> params = new HashMap<>();

        // Query
        sb.append("SELECT t FROM Transacao t ")
                .append(" JOIN t.lancamento l ")
                .append(" JOIN l.conta c ")
                .append("WHERE 1=1 ");

        LocalDate now = LocalDate.now();
        LocalDateTime dataInicio;
        LocalDateTime dataFim;

        if (mes == Month.FEBRUARY && conta.getDiaFechamento() > 28) {
            LocalDate lastDayOfFebruary = now.withMonth(Month.FEBRUARY.getValue()).withDayOfMonth(now.withMonth(Month.FEBRUARY.getValue()).lengthOfMonth());
            dataInicio = LocalDateTime.of(now.getYear(), Month.FEBRUARY, lastDayOfFebruary.getDayOfMonth(), 0, 0, 0).minusMonths(1).plusDays(1);
            dataFim = LocalDateTime.of(now.getYear(), Month.FEBRUARY, lastDayOfFebruary.getDayOfMonth(), 23, 59, 59);
        } else {
            dataInicio = LocalDateTime.of(now.getYear(), mes, conta.getDiaFechamento(), 0, 0, 0).minusMonths(1).plusDays(1);
            dataFim = LocalDateTime.of(now.getYear(), mes, conta.getDiaFechamento(), 23, 59, 59);
        }

        safeAddParams(params, "conta", conta,  sb, " AND c = :conta ");
        safeAddParams(params, "dtInicio", dataInicio,  sb, " AND t.dtVencimento >= :dtInicio ");
        safeAddParams(params, "dtFim", dataFim,  sb, " AND t.dtVencimento <= :dtFim ");

        sb.append(" ORDER BY c.nome, t.dtVencimento DESC, l.dtCriacao DESC ");

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
        safeAddParams(params, "pagamento", filter.getPagamento(), sb, " AND l.tipoPagamento = :pagamento ");
        safeAddParams(params, "dtInicio", filter.getDtInicio(), sb, " AND t.dtVencimento >= :dtInicio ");
        safeAddParams(params, "dtFim", filter.getDtFim(), sb, " AND t.dtVencimento <= :dtFim ");

        if(filter.getCategorias() != null && !filter.getCategorias().isEmpty()){
            safeAddParams(params, "categoria", filter.getCategorias(), sb, " AND l.categoriaLancamento IN :categoria ");
        }

        if(filter.getStatus() != null && !filter.getStatus().isEmpty()){
            safeAddParams(params, "status", filter.getStatus(), sb, " AND l.status IN :status ");
        }

        if(filter.getContaIds() != null && !filter.getContaIds().isEmpty() && filter.getContaIds().get(0) != 0){
            safeAddParams(params, "contaIds", filter.getContaIds(), sb, " AND c.id IN :contaIds ");
        }
    }
}
