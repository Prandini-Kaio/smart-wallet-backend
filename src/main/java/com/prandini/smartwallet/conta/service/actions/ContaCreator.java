package com.prandini.smartwallet.conta.service.actions;

/*
 * @author prandini
 * created 4/5/24
 */

import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.conta.model.ContaInput;
import com.prandini.smartwallet.conta.repository.ContaRepository;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@CommonsLog
public class ContaCreator {

    @Resource
    private ContaRepository repository;

    @Resource
    private ContaValidator validator;

    public Conta criarConta(ContaInput input){

        log.info(String.format("Criando conta %s para o banco %s.", input.getNome(), input.getBanco()));

        validator.validarCriacao(input);

        return repository.save(Conta.builder()
                        .banco(input.getBanco())
                        .nome(input.getNome())
                        .saldoParcial(BigDecimal.ZERO)
                .build());
    }
}
