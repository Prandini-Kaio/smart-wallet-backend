package com.prandini.smartwallet.assinatura.service.actions;

import com.prandini.smartwallet.assinatura.domain.Assinatura;
import com.prandini.smartwallet.assinatura.model.AssinaturaInput;
import com.prandini.smartwallet.assinatura.repository.AssinaturaRepository;
import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.conta.service.actions.ContaGetter;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.sql.init.dependency.DatabaseInitializationDependencyConfigurer;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/*
 * @author prandini
 * created 9/4/24
 */

@Component
public class AssinaturaCreator {

    @Resource
    private AssinaturaRepository repository;

    @Resource
    private ContaGetter contaGetter;
    @Autowired
    private DatabaseInitializationDependencyConfigurer databaseInitializationDependencyConfigurer;

    public Assinatura create(AssinaturaInput input) {

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
