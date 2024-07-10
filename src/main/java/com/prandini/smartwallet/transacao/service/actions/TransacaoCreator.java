package com.prandini.smartwallet.transacao.service.actions;

/*
 * @author prandini
 * created 4/16/24
 */

import com.prandini.smartwallet.common.LocalDateConverter;
import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.domain.TransacaoStatusEnum;
import com.prandini.smartwallet.transacao.repository.TransacaoRepository;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
@CommonsLog
public class TransacaoCreator {

    @Resource
    private TransacaoRepository repository;

    public List<Transacao> create(Lancamento lancamento){

        log.info(String.format("Gerando %s transações a partir do lançamento %s a partida da data %s.", lancamento.getParcelas(), lancamento.getId(), LocalDateConverter.toBrazilianDateTimeString(lancamento.getDtCriacao())));

        int parcelas = lancamento.getParcelas();

        BigDecimal valorPorParcela = lancamento.getValor().divide(BigDecimal.valueOf(parcelas), RoundingMode.CEILING);

        List<Transacao> transacoes = IntStream.range(0, parcelas)
                        .mapToObj(i -> Transacao.builder()
                                .valor(valorPorParcela)
                                .dtVencimento(calcularDataVencimento(lancamento.getDtCriacao(), i))
                                .lancamento(lancamento)
                                .status(TransacaoStatusEnum.PENDENTE)
                                .descricao(" [" + (i+1) + " / " + parcelas + "]")
                                .build())
                .toList();

        repository.saveAll(transacoes);

        return transacoes;
    }

    private LocalDateTime calcularDataVencimento(LocalDateTime dtCriacao, int indiceParcela) {
        return LocalDateTime.now().plusMonths(indiceParcela + 1);
    }
}
