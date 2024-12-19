package com.prandini.smartwallet.lancamento.service;

/*
 * @author prandini
 * created 12/18/24
 */

import com.prandini.smartwallet.lancamento.converter.SaldoProjetadoConverter;
import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.lancamento.model.LancamentoFilter;
import com.prandini.smartwallet.lancamento.model.SaldoProjetadoOutput;
import com.prandini.smartwallet.lancamento.service.actions.LancamentoGetter;
import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.model.TransacaoFilter;
import com.prandini.smartwallet.transacao.service.actions.TransacaoGetter;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaldoProjetadoService {

    @Resource
    private SaldoProjetadoConverter converter;

    @Resource
    private LancamentoGetter lancamentoGetter;

    @Resource
    private TransacaoGetter transacaoGetter;

    public List<SaldoProjetadoOutput> getSaldoProjetado(LancamentoFilter filter) {
        List<SaldoProjetadoOutput> saldoProjetados = new ArrayList<>();

        LocalDateTime dataInicio = filter.getDtInicio();
        LocalDateTime dataFim = filter.getDtFim();

        if (dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("Data de início não pode ser posterior à data de fim");
        }

        // Iterar pelos meses no intervalo
        while (!dataInicio.isAfter(dataFim)) {
            // Criar um novo filtro para o mês atual
            LancamentoFilter mesFilter = filter;
            mesFilter.setDtInicio(dataInicio.withDayOfMonth(1));
            mesFilter.setDtFim(dataInicio.withDayOfMonth(dataInicio.toLocalDate().lengthOfMonth()));

            List<Lancamento> lancamentos = lancamentoGetter.findByFilter(mesFilter);

            saldoProjetados.add(converter.toOutputLancamento(lancamentos, dataInicio));

            dataInicio = dataInicio.plusMonths(1);
        }

        return saldoProjetados;
    }

    public List<SaldoProjetadoOutput> getSaldoProjetado(TransacaoFilter filter) {
        List<SaldoProjetadoOutput> saldoProjetados = new ArrayList<>();

        LocalDateTime dataInicio = filter.getDtInicio();
        LocalDateTime dataFim = filter.getDtFim();

        if (dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("Data de início não pode ser posterior à data de fim");
        }

        // Iterar pelos meses no intervalo
        while (!dataInicio.isAfter(dataFim)) {
            // Criar um novo filtro para o mês atual
            TransacaoFilter mesFilter = filter;
            mesFilter.setDtInicio(dataInicio.withDayOfMonth(1));
            mesFilter.setDtFim(dataInicio.withDayOfMonth(dataInicio.toLocalDate().lengthOfMonth()));

            List<Transacao> transacoes = transacaoGetter.byFilter(mesFilter);

            saldoProjetados.add(converter.toOutputTransacao(transacoes, dataInicio));

            dataInicio = dataInicio.plusMonths(1);
        }

        return saldoProjetados;
    }
}
