package com.prandini.smartwallet.conta.service.actions;

/*
 * @author prandini
 * created 4/16/24
 */

import com.prandini.smartwallet.common.exception.CommonExceptionSupplier;
import com.prandini.smartwallet.common.model.AutcompleteDTO;
import com.prandini.smartwallet.common.model.TotalizadorFinanceiro;
import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.conta.model.ContaFilter;
import com.prandini.smartwallet.conta.model.ContaInput;
import com.prandini.smartwallet.conta.repository.ContaRepository;
import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.lancamento.domain.TipoLancamentoEnum;
import com.prandini.smartwallet.lancamento.service.actions.LancamentoGetter;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Component
@CommonsLog
public class ContaGetter {

    @Resource
    private ContaRepository repository;

    @Resource
    private LancamentoGetter lancamentoGetter;

    public List<Conta> getAll(){
        log.info("Buscando todas as contas.");

        return repository.findAll();
    }

    public Conta byNome(String nome){
        log.info(String.format("Buscando contas por nome %s.", nome));

        return repository.getContaByFilter(nome).orElseThrow(CommonExceptionSupplier.naoEncontrado("Conta", nome));
    }

    public List<Conta> byFilter(ContaFilter filter){
        log.info("Buscando contas por um filtro.");

        return repository.byFilter(filter);
    }

    public boolean existsContaByNomeBanco(ContaInput input) {
        return repository.existsContaByNomeBanco(input.getNome(), input.getBanco());
    }

    public List<AutcompleteDTO> autocompleteContas(String conta) {
        return this.repository.autcompleteContas(conta);
    }

    public Conta byid(Long id) {
        return repository.findById(id).orElseThrow(CommonExceptionSupplier.naoEncontrado("Conta"));
    }

    public BigDecimal getSaldoParcialConta(Long id){
        List<Lancamento> lancamentos = lancamentoGetter.getByConta(id);

        BigDecimal saldoParcial = lancamentos.stream()
                .filter(Objects::nonNull)
                .map(lancamento -> {
                    return lancamento.isEntrada() ?
                            lancamento.getValorBruto() :
                            lancamento.getValorBruto().negate();
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return saldoParcial;
    }

    public TotalizadorFinanceiro getTotalizadorByFilter(ContaFilter filter){
        return this.repository.totalizadorByFilter(filter);
    }
}
