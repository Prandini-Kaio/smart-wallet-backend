package com.prandini.smartwallet.fluxoCaixa.service.action;

import com.prandini.smartwallet.common.utils.DateUtils;
import com.prandini.smartwallet.conta.converter.ContaConverter;
import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.conta.service.actions.ContaGetter;
import com.prandini.smartwallet.fluxoCaixa.model.FluxoCaixaProjetadoFilter;
import com.prandini.smartwallet.fluxoCaixa.model.FluxoCaixaProjetadoOutput;
import com.prandini.smartwallet.fluxoCaixa.model.LancamentosProjetadosOutput;
import com.prandini.smartwallet.lancamento.service.actions.LancamentoGetter;
import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.service.actions.TransacaoGetter;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author kaiooliveira
 * created 04/01/2025
 */

@Service
@CommonsLog
public class FluxoCaixaProjetadoGetter {

    @Resource
    private LancamentoGetter lancamentoGetter;

    @Resource
    private TransacaoGetter transacaoGetter;

    @Resource
    private ContaConverter contaConverter;

    @Resource
    private ContaGetter contaGetter;

    public FluxoCaixaProjetadoOutput getResumoProjetadoByFilter(FluxoCaixaProjetadoFilter filter) {

        log.info("Calculando resumo de fluxo de caixa projetado.");

        FluxoCaixaProjetadoOutput resumo = FluxoCaixaProjetadoOutput.builder().build();

        List<LancamentosProjetadosOutput> lancamentosOutput = new ArrayList<>();

        this.calculaSaldoInicial(filter, resumo);
        this.calculaSaldoProjetado(filter, lancamentosOutput, resumo);

        lancamentosOutput = lancamentosOutput.stream().sorted(Comparator.comparing(LancamentosProjetadosOutput::getDtVencimento).reversed()).collect(Collectors.toList());

        resumo.addAllLancamentos(lancamentosOutput);

        resumo.setEntradas(lancamentosOutput.stream().map(LancamentosProjetadosOutput::getEntradas).reduce(BigDecimal.ZERO, BigDecimal::add));
        resumo.setSaidas(lancamentosOutput.stream().map(LancamentosProjetadosOutput::getSaidas).reduce(BigDecimal.ZERO, BigDecimal::add));

        return resumo;
    }

    private void calculaSaldoInicial(FluxoCaixaProjetadoFilter filter, FluxoCaixaProjetadoOutput resumo) {

        List<Conta> contas = new ArrayList<>();

        if(filter.getContaIds() != null && !filter.getContaIds().isEmpty()){
            contas = filter.getContaIds().stream().map(contaGetter::byId).toList();
        }else {
            contas = contaGetter.findAll();
        }

        List<Transacao> transacoes = new ArrayList<>();
        YearMonth mesAno;
        for (int i = filter.getMes().getValue() - 1; i > 0; i--){
            mesAno = YearMonth.of(LocalDate.now().getYear(), Month.of(i));
            transacoes.addAll(transacaoGetter.byContasMes(contas, mesAno));
        }

        mesAno = YearMonth.of(LocalDate.now().getYear() -1, Month.of(12));
        transacoes.addAll(transacaoGetter.byContasMes(contas, mesAno));

        BigDecimal saldoAnterior = transacoes.stream().map(Transacao::getValorComSinal).reduce(BigDecimal.ZERO, BigDecimal::add);
        resumo.setSaldoAnterior(saldoAnterior);
    }

    private void calculaSaldoProjetado(FluxoCaixaProjetadoFilter filter, List<LancamentosProjetadosOutput> lancamentosOutput, FluxoCaixaProjetadoOutput resumo) {

        List<Conta> contas = new ArrayList<>();

        if(filter.getContaIds() != null && !filter.getContaIds().isEmpty()){
            contas = filter.getContaIds().stream().map(contaGetter::byId).toList();
        }else {
            contas = contaGetter.findAll();
        }

        YearMonth mesAno = YearMonth.of(LocalDate.now().getYear(), filter.getMes());
        List<Transacao> transacoes = transacaoGetter.byContasMes(contas, mesAno);

        BigDecimal saldoProjetado = resumo.getSaldoAnterior();

        for (Transacao transacao : transacoes){
            Optional<LancamentosProjetadosOutput> any = lancamentosOutput.stream().filter(l -> l.getConta().equals(contaConverter.toOutput(transacao.getLancamento().getContaDestino()))).findAny();
            LancamentosProjetadosOutput output = any.orElseGet(() -> LancamentosProjetadosOutput.builder().build());
            output.setConta(contaConverter.toOutput(transacao.getLancamento().getContaDestino()));
            output.setCategoria(transacao.getLancamento().getCategoriaLancamento());
            output.setDtVencimento(DateUtils.toBrazilianDateTimeString(transacao.getDtVencimento()));
            output.setDescricao(transacao.getDescricaoCompleta());
            output.setStatus(transacao.getStatus().getDescricao());
            output.addValor(transacao.getValor(), transacao.getLancamento().getTipoLancamento());

            if(!lancamentosOutput.contains(output)){
                lancamentosOutput.add(output);
            }
        }

        saldoProjetado = saldoProjetado.add(lancamentosOutput.stream()
                        .map(LancamentosProjetadosOutput::getEntradas)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .subtract(lancamentosOutput.stream()
                        .map(LancamentosProjetadosOutput::getSaidas)
                        .reduce(BigDecimal.ZERO, BigDecimal::add));

        resumo.setSaldoProjetado(saldoProjetado);
    }
}
