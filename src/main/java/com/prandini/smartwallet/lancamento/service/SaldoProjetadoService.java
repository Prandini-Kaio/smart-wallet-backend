package com.prandini.smartwallet.lancamento.service;

/*
 * @author prandini
 * created 12/18/24
 */

import com.prandini.smartwallet.common.model.ResumoFinanceiroOutput;
import com.prandini.smartwallet.conta.converter.ContaConverter;
import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.conta.service.actions.ContaGetter;
import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.lancamento.domain.TipoLancamentoEnum;
import com.prandini.smartwallet.lancamento.model.LancamentoFilter;
import com.prandini.smartwallet.lancamento.model.ResumoFinanceiroFilter;
import com.prandini.smartwallet.lancamento.model.ResumoFinanceiroListOutput;
import com.prandini.smartwallet.lancamento.service.actions.LancamentoGetter;
import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.model.TransacaoFilter;
import com.prandini.smartwallet.transacao.service.actions.TransacaoGetter;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

        if(filter.getContaIds() == null){
            contas = contaGetter.findAll();
        }else {
            contas = filter.getContaIds().stream().map(contaGetter::byId).toList();
        }

        transacoes = this.transacaoGetter.byContasMes(contas, filter.getMes());

        List<ResumoFinanceiroOutput> resumos = new ArrayList<>();

        // Cria um resumo pra cada conta, mesmo que zerado
        contas.stream().map(conta -> ResumoFinanceiroOutput.builder().conta(contaConverter.toOutput(conta)).build()).forEach(resumos::add);

        for (Transacao transacao : transacoes) {

            Optional<ResumoFinanceiroOutput> any = resumos.stream().filter(r -> r.getConta().getId().equals(transacao.getLancamento().getConta().getId())).findAny();
            ResumoFinanceiroOutput resumo = any.orElseGet(() -> ResumoFinanceiroOutput.builder().build());

            resumo.setMes(filter.getMes());
            resumo.setConta(contaConverter.toOutput(transacao.getLancamento().getConta()));
            resumo.addValor(transacao.getValor(), transacao.getLancamento().getTipoLancamento());

            if(!resumos.contains(resumo)){
                resumos.add(resumo);
            }
        }

        return resumos;
    }
}
