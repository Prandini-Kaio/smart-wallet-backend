package com.prandini.smartwallet.lancamento.service;

/*
 * @author prandini
 * created 12/18/24
 */

import com.prandini.smartwallet.common.model.ResumoFinanceiroOutput;
import com.prandini.smartwallet.conta.converter.ContaConverter;
import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.lancamento.domain.TipoLancamentoEnum;
import com.prandini.smartwallet.lancamento.model.LancamentoFilter;
import com.prandini.smartwallet.lancamento.model.ResumoFinanceiroFilter;
import com.prandini.smartwallet.lancamento.model.ResumoFinanceiroListOutput;
import com.prandini.smartwallet.lancamento.service.actions.LancamentoGetter;
import com.prandini.smartwallet.transacao.domain.Transacao;
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
    private ContaConverter contaConverter;

    public List<ResumoFinanceiroOutput> getResumoFinanceiro(ResumoFinanceiroFilter filter) {
        log.info("Iniciando calculo de resumo financeiro.");

        List<Lancamento> lancamentos = this.lancamentoGetter.findByFilter(LancamentoFilter.builder()
                .contaIds(filter.getContaIds() != null ? filter.getContaIds() : null)
                .tipo(filter.getTipo())
                .pagamento(filter.getPagamento())
                .categorias(filter.getCategorias())
                .build()
        );

        List<ResumoFinanceiroOutput> resumos = new ArrayList<>();

        for (Lancamento lancamento : lancamentos) {
            List<Transacao> transacoes = lancamento.getTransacoes();

            Optional<ResumoFinanceiroOutput> any = resumos.stream().filter(r -> r.getConta().getId().equals(lancamento.getConta().getId()) && r.getMes().equals(filter.getMes().getMonth())).findAny();
            ResumoFinanceiroOutput resumo = any.orElseGet(() -> ResumoFinanceiroOutput.builder().build());

            resumo.setConta(contaConverter.toOutput(lancamento.getConta()));
            resumo.setMes(filter.getMes().getMonth());

            BigDecimal entradas = transacoes.stream()
                    .filter(t -> t.getDtVencimento().getMonth().equals(filter.getMes().getMonth()))
                    .filter(t -> t.getLancamento().getTipoLancamento().equals(TipoLancamentoEnum.ENTRADA))
                    .map(Transacao::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal saidas = transacoes.stream()
                    .filter(t -> t.getDtVencimento().getMonth().equals(filter.getMes().getMonth()))
                    .filter(t -> t.getLancamento().getTipoLancamento().equals(TipoLancamentoEnum.SAIDA))
                    .map(Transacao::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);

            resumo.setEntradas(entradas);
            resumo.setSaidas(saidas);

            resumos.add(resumo);
        }

        return resumos;
    }
}
