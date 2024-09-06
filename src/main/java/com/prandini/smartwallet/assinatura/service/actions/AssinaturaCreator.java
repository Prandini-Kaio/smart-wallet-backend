package com.prandini.smartwallet.assinatura.service.actions;

import com.prandini.smartwallet.assinatura.domain.Assinatura;
import com.prandini.smartwallet.assinatura.model.AssinaturaInput;
import com.prandini.smartwallet.assinatura.repository.AssinaturaRepository;
import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.conta.service.actions.ContaGetter;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/*
 * @author prandini
 * created 9/4/24
 */

@Component
@CommonsLog
public class AssinaturaCreator {

    @Resource
    private AssinaturaRepository repository;

    @Resource
    private ContaGetter contaGetter;

    public Assinatura create(AssinaturaInput input) {

        log.info(String.format("Criando assinatura para %s", input.getDescricao()));

        Conta conta = contaGetter.getContaByFilter(input.getConta());

        Assinatura assinatura = Assinatura.builder()
                .conta(conta)
                .valor(input.getValor())
                .dtInicio(input.getDtInicio() != null ? input.getDtInicio() : LocalDate.now())
                .dtFim(input.getDtFim() != null ? input.getDtFim() : null)
                .descricao(input.getDescricao())
                .ativa(input.isAtiva())
                .build();

        return repository.save(assinatura);
    }
}
