package com.prandini.smartwallet.lancamento.service;

/*
 * @author prandini
 * created 12/18/24
 */

import com.prandini.smartwallet.common.model.ResumoFinanceiroOutput;
import com.prandini.smartwallet.conta.converter.ContaConverter;
import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.conta.model.ContaFilter;
import com.prandini.smartwallet.conta.service.actions.ContaGetter;
import com.prandini.smartwallet.lancamento.model.ResumoFinanceiroFilter;
import com.prandini.smartwallet.lancamento.service.actions.LancamentoGetter;
import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.service.actions.TransacaoGetter;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@CommonsLog
public class SaldoProjetadoService {

    @Resource
    private LancamentoGetter lancamentoGetter;

    @Resource
    private TransacaoGetter transacaoGetter;

    @Resource
    private ContaGetter contaGetter;

    @Resource
    private ContaConverter contaConverter;

    public List<ResumoFinanceiroOutput> getResumoFinanceiro(ResumoFinanceiroFilter filter) {
        log.info("Iniciando calculo de resumo financeiro.");

        List<Transacao> transacoes = new ArrayList<>();
        List<Conta> contas = new ArrayList<>();

        if(filter.getContaDestinoIds() == null){
            contas = contaGetter.byFilter(ContaFilter.builder().build());
        }else {
            contas = filter.getContaDestinoIds().stream().map(contaGetter::byId).toList();
        }

        YearMonth mesAno = YearMonth.of(LocalDate.now().getYear(), filter.getMes());
        transacoes = this.transacaoGetter.byContasMes(contas, mesAno);

        List<ResumoFinanceiroOutput> resumos = new ArrayList<>();

        // Cria um resumo pra cada conta, mesmo que zerado
        contas.stream().map(conta -> ResumoFinanceiroOutput.builder().conta(contaConverter.toOutput(conta)).build()).forEach(resumos::add);

        for (Transacao transacao : transacoes) {

            Optional<ResumoFinanceiroOutput> any = resumos.stream().filter(r -> r.getConta().getId().equals(transacao.getLancamento().getContaDestino().getId())).findAny();
            ResumoFinanceiroOutput resumo = any.orElseGet(() -> ResumoFinanceiroOutput.builder().build());

            resumo.setMes(filter.getMes());
            resumo.setConta(contaConverter.toOutput(transacao.getLancamento().getContaDestino()));
            resumo.addValor(transacao.getValor(), transacao.getLancamento().getTipoLancamento());

            if(!resumos.contains(resumo)){
                resumos.add(resumo);
            }
        }

        return resumos.stream().sorted(Comparator.comparing(r -> r.getConta().getBanco())).collect(Collectors.toList());
    }
}
