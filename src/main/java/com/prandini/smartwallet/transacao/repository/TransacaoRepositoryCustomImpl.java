package com.prandini.smartwallet.transacao.repository;

import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.lancamento.domain.CategoriaLancamentoEnum;
import com.prandini.smartwallet.lancamento.domain.TipoLancamentoEnum;
import com.prandini.smartwallet.lancamento.domain.TipoPagamentoEnum;
import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.model.TransacaoFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
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
                .append(" JOIN l.contaDestino cd ")
                .append(" LEFT JOIN l.contaOrigem co ")
                .append("WHERE 1=1 ");

        Optional.ofNullable(filter).ifPresent(f -> buildParams(params, sb, f));

        sb.append(" ORDER BY t.dtVencimento DESC, l.dtCriacao DESC ");

        // Criando a query com base no StringBuilder
        Query query = this.entityManager.createQuery(sb.toString());

        params.forEach(query::setParameter);

        return query.getResultList();
    }

    @Override
    public List<Transacao> bySaidasCreditoVencimentoConta(Conta conta, YearMonth mesAno) {
        StringBuilder sb = new StringBuilder();

        Map<String, Object> params = new HashMap<>();

        // Query
        sb.append("SELECT t FROM Transacao t ")
                .append(" JOIN t.lancamento l ")
                .append(" JOIN l.contaDestino cd ")
                .append(" LEFT JOIN l.contaOrigem co ")
                .append("WHERE 1=1 ");

        LocalDateTime[] periodo = this.calcularPeriodoCredito(conta.getDiaFechamento(), conta.getDiaVencimento(), mesAno);

        safeAddParams(params, "tipo", TipoLancamentoEnum.SAIDA, sb, " AND l.tipoLancamento = :tipo");
        safeAddParams(params, "pagamento", TipoPagamentoEnum.CREDITO, sb, " AND l.tipoPagamento = :pagamento");
        safeAddParams(params, "contaDestino", conta, sb, " AND cd = :contaDestino ");
        safeAddParams(params, "dtInicio", periodo[0],  sb, " AND t.dtVencimento >= :dtInicio ");
        safeAddParams(params, "dtFim", periodo[1],  sb, " AND t.dtVencimento <= :dtFim ");

        sb.append(" ORDER BY cd.banco, t.dtVencimento DESC, l.dtCriacao DESC ");

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
        safeAddParams(params, "categoria", CategoriaLancamentoEnum.PAGAMENTO, sb, " AND l.categoriaLancamento NOT IN :categoria ");
        safeAddParams(params, "dtInicio", filter.getDtInicio(), sb, " AND t.dtVencimento >= :dtInicio ");
        safeAddParams(params, "dtFim", filter.getDtFim(), sb, " AND t.dtVencimento <= :dtFim ");

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

    private LocalDateTime[] calcularPeriodoCredito(int diaFechamento, int diaVencimento, YearMonth mesAno){
        LocalDateTime dataInicio;
        LocalDateTime dataFim;

        try {
            YearMonth mesAnoInicio = mesAno;
            mesAnoInicio = mesAnoInicio.minusMonths(1);
            int diaConsulta = diaFechamento;

            if(diaFechamento > diaVencimento){
                mesAnoInicio = mesAnoInicio.minusMonths(1);
                diaConsulta = Math.min(mesAnoInicio.lengthOfMonth(), diaFechamento);
            }

            dataInicio = LocalDateTime.of(mesAnoInicio.getYear(), mesAnoInicio.getMonth(), diaConsulta, 0, 0, 0);
            dataInicio = dataInicio.plusDays(1);

            YearMonth mesAnoFim = mesAno;
            diaConsulta = diaFechamento;

            if(diaFechamento > diaVencimento){
                mesAnoFim = mesAnoFim.minusMonths(1);
                diaConsulta = Math.min(mesAnoFim.lengthOfMonth(), diaFechamento);
            }

            dataFim = LocalDateTime.of(mesAnoFim.getYear(), mesAnoFim.getMonth(), diaConsulta, 23, 59, 59);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new LocalDateTime[]{dataInicio, dataFim};
    }

}
