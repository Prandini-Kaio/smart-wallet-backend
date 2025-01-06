package com.prandini.smartwallet.conta.service.actions;

/*
 * @author prandini
 * created 4/16/24
 */

import com.prandini.smartwallet.common.exception.CommonExceptionSupplier;
import com.prandini.smartwallet.common.model.TotalizadorFinanceiro;
import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.conta.model.ContaFilter;
import com.prandini.smartwallet.conta.model.ContaInput;
import com.prandini.smartwallet.conta.repository.ContaRepository;
import com.prandini.smartwallet.transacao.model.TransacaoFilter;
import com.prandini.smartwallet.transacao.service.actions.TransacaoGetter;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component
@CommonsLog
public class ContaGetter {

    @Resource
    private ContaRepository repository;

    @Resource
    private TransacaoGetter transacaoGetter;

    public List<Conta> byFilter(ContaFilter filter){
        log.info(String.format("Buscando contas por filtro %s.", filter));
        return repository.byFilter(filter);
    }

    public Conta findByFilter(ContaFilter filter){
        log.info(String.format("Buscando contas por filtro %s.", filter));
        return repository.optionalByFilter(filter).orElseThrow(CommonExceptionSupplier.naoEncontrado("Conta", filter.getNome()));
    }

    public boolean existsContaByNomeBanco(ContaInput input) {
        return repository.existsContaByNomeBanco(input.getNome(), input.getBanco());
    }

    public Conta byId(Long id) {
        log.info(String.format("Buscando conta por id %s.", id));
        return repository.findById(id).orElseThrow(CommonExceptionSupplier.naoEncontrado("Conta"));
    }

    public BigDecimal getSaldoParcialConta(Conta conta){
        LocalDateTime now = LocalDateTime.now();
        BigDecimal saldoParcial = transacaoGetter.byFilter(TransacaoFilter.builder().contaIds(List.of(conta.getId())).build()).stream()
                .filter(Objects::nonNull)
                .filter(transacao -> transacao.getDtVencimento().isBefore(LocalDateTime.of(now.getYear(), now.getMonth(), conta.getDiaVencimento(), 23, 59, 59).plusMonths(1)))
                .map(transacao -> {
                    return transacao.getLancamento().isEntrada() ?
                            transacao.getValor() :
                            transacao.getValor().negate();
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return saldoParcial;
    }

    public TotalizadorFinanceiro getTotalizadorByFilter(ContaFilter filter){
        return this.repository.totalizadorByFilter(filter);
    }
}
