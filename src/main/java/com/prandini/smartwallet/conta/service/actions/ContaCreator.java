package com.prandini.smartwallet.conta.service.actions;

/*
 * @author prandini
 * created 4/5/24
 */

import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.conta.model.ContaInput;
import com.prandini.smartwallet.conta.repository.ContaRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ContaCreator {

    @Resource
    private ContaRepository repository;

    public Conta criarConta(ContaInput input){
        return repository.save(Conta.builder()
                        .banco(input.getBanco())
                        .nome(input.getNome())
                        .saldoParcial(BigDecimal.ZERO)
                .build());
    }
}
