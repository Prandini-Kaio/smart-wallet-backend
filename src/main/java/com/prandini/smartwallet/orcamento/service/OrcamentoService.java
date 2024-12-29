package com.prandini.smartwallet.orcamento.service;

import com.prandini.smartwallet.lancamento.domain.CategoriaLancamentoEnum;
import com.prandini.smartwallet.orcamento.model.OrcamentoFilter;
import com.prandini.smartwallet.orcamento.model.OrcamentoInput;
import com.prandini.smartwallet.orcamento.model.OrcamentoOutput;
import com.prandini.smartwallet.orcamento.service.action.OrcamentoConverter;
import com.prandini.smartwallet.orcamento.service.action.OrcamentoCreator;
import com.prandini.smartwallet.orcamento.service.action.OrcamentoDeleter;
import com.prandini.smartwallet.orcamento.service.action.OrcamentoGetter;
import com.prandini.smartwallet.orcamento.service.action.OrcamentoUpdater;
import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.model.TransacaoFilter;
import com.prandini.smartwallet.transacao.service.actions.TransacaoGetter;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

/**
 * @author kaiooliveira
 * created 27/12/2024
 */

@Service
@CommonsLog
public class OrcamentoService {

    @Resource
    private OrcamentoGetter getter;

    @Resource
    private OrcamentoCreator creator;

    @Resource
    private OrcamentoUpdater updater;

    @Resource
    private OrcamentoDeleter deleter;

    @Resource
    private OrcamentoConverter converter;

    @Resource
    private TransacaoGetter transacaoGetter;

    public List<OrcamentoOutput> listarOrcamentos(Month mes) {
        log.info("Iniciando busca de orcamentos cadastrados.");

        LocalDateTime dtInicio = LocalDateTime.of(LocalDateTime.now().getYear(), mes, 1, 0, 0).plusMonths(1);
        LocalDateTime dtFim = dtInicio.plusMonths(1).minusSeconds(1);

        List<Transacao> transacoes = transacaoGetter.byFilter(TransacaoFilter.builder()
                .dtInicio(dtInicio)
                .dtFim(dtFim)
                .build()
        );

        Map<CategoriaLancamentoEnum, List<Transacao>> transacoesPorCategoria = transacoes.stream()
                .collect(groupingBy(t -> t.getLancamento().getCategoriaLancamento()));

        return this.converter.toOutput(getter.listarOrcamentos(), transacoesPorCategoria);
    }

    public OrcamentoOutput criarOrcamento(OrcamentoInput input) {
        log.info("Iniciando criação de novo orçamento.");
        return this.converter.toOutput(this.creator.criarOrcamento(input));
    }

    @Transactional
    public OrcamentoOutput atualizarOrcamento(OrcamentoInput input) {
        log.info("Iniciando atualização de orçamento.");
        return this.converter.toOutput(this.updater.atualizarOrcamento(input));
    }

    public void delete(Long id){
        log.info("Iniciando exclusão de orçamento.");
        this.deleter.deletarOrcamento(id);
    }
}
