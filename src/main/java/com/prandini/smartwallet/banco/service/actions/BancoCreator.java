package com.prandini.smartwallet.banco.service.actions;

import com.prandini.smartwallet.banco.domain.Banco;
import com.prandini.smartwallet.banco.model.BancoInput;
import com.prandini.smartwallet.banco.repository.BancoRepository;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

/**
 * @author kaiooliveira
 * created 15/05/2025
 */

@Component
@CommonsLog
public class BancoCreator {

    @Resource
    private BancoRepository repository;

    public Banco createFromInput(BancoInput input) {
        log.info("Criando banco com nome: " + input.getNome());

        Banco banco = new Banco();
        banco.setNome(input.getNome());
        return repository.save(banco);
    }
}
