package com.prandini.smartwallet.transacao.service.actions;

import com.prandini.smartwallet.common.utils.DateUtils;
import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.conta.service.actions.ContaGetter;
import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.lancamento.domain.TipoLancamentoEnum;
import com.prandini.smartwallet.lancamento.domain.TipoPagamentoEnum;
import com.prandini.smartwallet.lancamento.model.LancamentoInput;
import com.prandini.smartwallet.transacao.domain.StatusTransacaoEnum;
import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.repository.TransacaoRepository;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@CommonsLog
public class TransacaoCreator {

    @Resource
    private TransacaoRepository repository;

    @Resource
    private ContaGetter contaGetter;

    public List<Transacao> fromInput(LancamentoInput input) {
        List<BigDecimal> valorParcelas = calcularParcelas(input.getValor(), input.getParcelas());

        Conta conta = contaGetter.findByFilter(input.getConta());
        List<Transacao> transacoes = IntStream.range(0, input.getParcelas())
                .mapToObj(i -> buildTransacao(input, valorParcelas.get(i), conta.getDiaVencimento(), i))
                .toList();

        return this.repository.saveAll(transacoes);
    }

    public List<Transacao> create(Lancamento lancamento) {
        log.info(String.format("Gerando %s transações do lançamento %s a partir da data %s.",
                lancamento.getParcelas(), lancamento.getId(), DateUtils.toBrazilianDateTimeString(lancamento.getDtCriacao())));

        List<BigDecimal> parcelas = calcularParcelas(lancamento.getValorBruto(), lancamento.getParcelas());

        List<Transacao> transacoes = gerarTransacoes(lancamento, parcelas);

        ajustarTransacoesEncadeadas(transacoes);

        return transacoes;
    }

    private List<Transacao> gerarTransacoes(Lancamento lancamento, List<BigDecimal> parcelas) {
        return IntStream.range(0, lancamento.getParcelas())
                .mapToObj(i -> buildTransacao(lancamento, parcelas.get(i), lancamento.getConta().getDiaVencimento(), i))
                .collect(Collectors.toList());
    }

    private void ajustarTransacoesEncadeadas(List<Transacao> transacoes) {
        IntStream.range(0, transacoes.size() - 1)
                .forEach(i -> transacoes.get(i).setProxima(transacoes.get(i + 1)));
        if (!transacoes.isEmpty()) {
            transacoes.get(transacoes.size() - 1).setProxima(null);
        }
    }

    private Transacao buildTransacao(LancamentoInput input, BigDecimal valorParcela, int diaVencimento, int indice) {
        return this.buildTransacao(input.getTipoLancamento(), input.getTipoPagamento(), input.getParcelas(), diaVencimento, input.getDtCriacao(), valorParcela, indice);
    }

    private Transacao buildTransacao(Lancamento lancamento, BigDecimal valorParcela, int diaVencimento, int indice) {
        return this.buildTransacao(lancamento.getTipoLancamento(), lancamento.getTipoPagamento(), lancamento.getParcelas(), diaVencimento, lancamento.getDtCriacao(), valorParcela, indice);
    }

    private Transacao buildTransacao(TipoLancamentoEnum tipo, TipoPagamentoEnum tipoPagamento, int parcelas, int diaVencimento, LocalDateTime dtCriacao, BigDecimal valorParcela, int indice) {
        boolean isEntrada = tipo.equals(TipoLancamentoEnum.ENTRADA);
        boolean isDebito = tipoPagamento.equals(TipoPagamentoEnum.DEBITO);

        return Transacao.builder()
                .valor(valorParcela)
                .status(isEntrada ? StatusTransacaoEnum.PAGO : StatusTransacaoEnum.PENDENTE)
                .descricao(String.format(" [%d / %d]", indice + 1, parcelas))
                .dtVencimento(isDebito ? LocalDateTime.now() : calcularDataVencimento(diaVencimento, dtCriacao, indice + 1))
                .dtPagamento(isEntrada ? LocalDateTime.now() : null)
                .build();
    }

    private List<BigDecimal> calcularParcelas(BigDecimal total, int numParcelas) {
        BigDecimal valorParcela = total.divide(BigDecimal.valueOf(numParcelas), 2, RoundingMode.DOWN);
        List<BigDecimal> parcelas = new ArrayList<>(numParcelas);

        IntStream.range(0, numParcelas).forEach(i -> parcelas.add(valorParcela));

        BigDecimal somaParcelas = valorParcela.multiply(BigDecimal.valueOf(numParcelas - 1));
        BigDecimal ultimaParcela = total.subtract(somaParcelas);

        parcelas.set(numParcelas - 1, ultimaParcela);  // Corrige a última parcela

        return parcelas;
    }

    private LocalDateTime calcularDataVencimento(int diaVencimento, LocalDateTime dtCriacao, int indiceParcela) {
        return dtCriacao.plusMonths(indiceParcela).withDayOfMonth(diaVencimento).withHour(0).withMinute(0).withSecond(0);
    }
}
