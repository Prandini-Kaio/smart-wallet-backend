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

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dataInicio = LocalDateTime.now();
        LocalDateTime dataFim = LocalDateTime.now();

        LocalDateTime[] periodo = this.calcularPeriodo(conta.getDiaFechamento(), conta.getDiaVencimento(), mes);

        safeAddParams(params, "conta", conta,  sb, " AND c = :conta ");
        safeAddParams(params, "dtInicio", periodo[0],  sb, " AND t.dtVencimento >= :dtInicio ");
        safeAddParams(params, "dtFim", periodo[1],  sb, " AND t.dtVencimento <= :dtFim ");

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

    public LocalDateTime[] calcularPeriodo(LocalDateTime now, int diaFechamento, int diaVencimento, Month mes) {
        int mesConsulta = mes.getValue();
        int anoConsulta = now.getYear();

        // Ajustar mês e ano para o cálculo do fechamento e vencimento
        if (diaFechamento > diaVencimento) {
            // Caso o fechamento e vencimento sejam de meses diferentes
            mesConsulta -= 1;
            if (mesConsulta == 0) {
                mesConsulta = 12;
                anoConsulta -= 1;
            }
            mesConsulta -= 1;
        }

        // Obter o último dia do mês anterior ao mês de consulta
        YearMonth mesAnterior = YearMonth.of(anoConsulta, mesConsulta);
        int ultimoDiaMesAnterior = mesAnterior.lengthOfMonth();

        // Ajustar diaFechamento para não ultrapassar o último dia do mês
        int diaFechamentoAjustado = Math.min(diaFechamento, ultimoDiaMesAnterior);

        // Data de início: fechamento + 1 dia do mês anterior
        LocalDateTime dataInicio = LocalDateTime.of(anoConsulta, mesConsulta, diaFechamentoAjustado, 0, 0, 0).plusDays(1);

        // Calcular o próximo mês e ano para o fim do período
        int mesProximo = mesConsulta + 1;
        int anoProximo = anoConsulta;
        if (mesProximo > 12) {
            mesProximo = 1;
            anoProximo += 1;
        }

        // Obter o último dia do próximo mês
        YearMonth mesProximoObj = YearMonth.of(anoProximo, mesProximo);
        int ultimoDiaMesProximo = mesProximoObj.lengthOfMonth();

        // Ajustar diaFechamento para o próximo mês
        int diaFechamentoProximoAjustado = Math.min(diaFechamento, ultimoDiaMesProximo);

        // Data de fim: fechamento no próximo mês
        LocalDateTime dataFim = LocalDateTime.of(anoProximo, mesProximo, diaFechamentoProximoAjustado, 23, 59, 59);

        return new LocalDateTime[]{dataInicio, dataFim};
    }

    private LocalDateTime[] calcularPeriodo(int diaFechamento, int diaVencimento, Month mesConsulta){
        // BUSCAR LANCAMENTOS DE UMA DATA FECHAMENTO A OUTRA
        // MES DE FEVEREIRO

        LocalDateTime dataInicio;
        LocalDateTime dataFim;

        YearMonth mesAnoInicio = YearMonth.of(LocalDate.now().getYear(), mesConsulta);
        mesAnoInicio = mesAnoInicio.minusMonths(1);
        int diaConsulta = diaFechamento;

        if(diaFechamento > diaVencimento){
            mesAnoInicio = mesAnoInicio.minusMonths(1);
            diaConsulta = Math.min(mesAnoInicio.lengthOfMonth(), diaFechamento);
        }

        diaConsulta = diaConsulta + 1;

        dataInicio = LocalDateTime.of(mesAnoInicio.getYear(), mesAnoInicio.getMonth(), diaConsulta, 0, 0, 0);

        YearMonth mesAnoFim = YearMonth.of(LocalDate.now().getYear(), mesConsulta);
        diaConsulta = diaFechamento;

        if(diaFechamento > diaVencimento){
            mesAnoFim = mesAnoFim.minusMonths(1);
            diaConsulta = Math.min(mesAnoFim.lengthOfMonth(), diaFechamento);
        }

        dataFim = LocalDateTime.of(mesAnoFim.getYear(), mesAnoFim.getMonth(), diaConsulta, 23, 59, 59);

        return new LocalDateTime[]{dataInicio, dataFim};
    }

}
