package com.prandini.smartwallet.transacao.service.actions;

/*
 * @author prandini
 * created 4/16/24
 */

import com.prandini.smartwallet.common.utils.DateUtils;
import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.lancamento.domain.TipoLancamentoEnum;
import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.domain.StatusTransacaoEnum;
import com.prandini.smartwallet.transacao.repository.TransacaoRepository;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@CommonsLog
public class TransacaoCreator {

    @Resource
    private TransacaoRepository repository;

    public List<Transacao> create(Lancamento lancamento){

        log.info(String.format("Gerando %s transações do lançamento %s a partir da data %s.", lancamento.getParcelas(), lancamento.getId(), DateUtils.toBrazilianDateTimeString(lancamento.getDtCriacao())));

        List<Transacao> transacoes = IntStream
                .range(0, lancamento.getParcelas())
                .mapToObj(i -> buildTransacao(lancamento, i))
                .collect(Collectors.toList());

        IntStream.range(0, transacoes.size() - 1)
                .forEach(i -> transacoes.get(i).setProxima(transacoes.get(i + 1)));

        if (!transacoes.isEmpty()) {
            transacoes.get(transacoes.size() - 1).setProxima(null);
        }

        return repository.saveAll(transacoes);
    }

    private Transacao buildTransacao(Lancamento lancamento, int i){
        BigDecimal valor = lancamento.getValor()
                .divide(BigDecimal.valueOf(lancamento.getParcelas()), RoundingMode.CEILING);

        return Transacao.builder()
                .valor(valor)
                .lancamento(lancamento)
                .status(lancamento.getTipoLancamento().equals(TipoLancamentoEnum.ENTRADA) ? StatusTransacaoEnum.PAGO : StatusTransacaoEnum.PENDENTE)
                .descricao(" [" + (i+1) + " / " + lancamento.getParcelas() + "]")
                .dtVencimento(calcularDataVencimento(lancamento.getConta().getDiaVencimento(), lancamento.getDtCriacao(), i))
                .dtPagamento(lancamento.getTipoLancamento().equals(TipoLancamentoEnum.ENTRADA) ? LocalDateTime.now() : null)
                .build();
    }

    private LocalDateTime calcularDataVencimento(int diaVencimento, LocalDateTime dtCriacao, int indiceParcela) {
        LocalDateTime now = LocalDateTime.now();
        return LocalDateTime.of(now.getYear(), now.plusMonths(indiceParcela).getMonthValue(), diaVencimento, 0, 0, 0);
    }
}
