package com.prandini.smartwallet.conta.repository;

import com.prandini.smartwallet.common.model.TotalizadorFinanceiro;
import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.conta.model.ContaFilter;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.prandini.smartwallet.common.utils.QueryUtils.safeAddParams;

/*
 * @author prandini
 * created 9/22/24
 */
public class ContaRepositoryCustomImpl implements ContaRepositoryCustom{

    @Resource
    private EntityManager entityManager;

    @Override
    public List<Conta> byFilter(ContaFilter filter) {
        StringBuilder sb = new StringBuilder();

        Map<String, Object> params = new HashMap<>();

        // Query
        sb.append("SELECT c FROM Conta c ")
                .append("WHERE 1=1 ");

        safeAddParams(params, "nome", filter.getNome(), sb, " AND LOWER(c.nome) LIKE CONCAT('%', LOWER(:nome), '%') OR LOWER(c.banco) LIKE CONCAT('%', LOWER(:nome), '%') ");
        safeAddParams(params, "tipoConta", filter.getTipoConta(), sb, " AND c.tipoConta = :tipoConta ");
        safeAddParams(params, "diaVencimento", filter.getDiaVencimento(), sb, " AND c.diaVencimento = :diaVencimento ");

        sb.append(" ORDER BY c.saldoParcial DESC ");

        // Criando a query com base no StringBuilder
        Query query = this.entityManager.createQuery(sb.toString());

        params.forEach(query::setParameter);

        return query.getResultList();
    }

    @Override
    public TotalizadorFinanceiro totalizadorByFilter(ContaFilter filter) {
        StringBuilder sb = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        sb.append("SELECT new com.prandini.smartwallet.common.model.TotalizadorFinanceiro( ")
                .append("  SUM(CASE WHEN l.tipoLancamento = com.prandini.smartwallet.lancamento.domain.TipoLancamentoEnum.ENTRADA THEN l.valor ELSE 0 END) - SUM(CASE WHEN l.tipoLancamento = com.prandini.smartwallet.lancamento.domain.TipoLancamentoEnum.SAIDA THEN l.valor ELSE 0 END), ")
                .append("  SUM(CASE WHEN l.tipoLancamento = com.prandini.smartwallet.lancamento.domain.TipoLancamentoEnum.ENTRADA THEN l.valor ELSE 0 END), ")
                .append("  SUM(CASE WHEN l.tipoLancamento = com.prandini.smartwallet.lancamento.domain.TipoLancamentoEnum.SAIDA THEN l.valor ELSE 0 END) ")
                .append(") ")
                .append("FROM Lancamento l ")
                .append("JOIN l.conta c ")
                .append("WHERE 1=1 ");

        safeAddParams(params, "nome", filter.getNome(), sb, " AND (LOWER(c.nome) LIKE CONCAT('%', LOWER(:nome), '%') OR LOWER(c.banco) LIKE CONCAT('%', LOWER(:nome), '%')) ");
        safeAddParams(params, "tipoConta", filter.getTipoConta(), sb, " AND c.tipoConta = :tipoConta ");
        safeAddParams(params, "diaVencimento", filter.getDiaVencimento(), sb, " AND c.diaVencimento = :diaVencimento ");

        Query query = this.entityManager.createQuery(sb.toString());

        params.forEach(query::setParameter);

        return (TotalizadorFinanceiro) query.getSingleResult();
    }

}
