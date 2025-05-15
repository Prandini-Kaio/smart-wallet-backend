package com.prandini.smartwallet.banco.service.actions;

import com.prandini.smartwallet.banco.domain.Banco;
import com.prandini.smartwallet.banco.repository.BancoRepository;
import com.prandini.smartwallet.common.exception.CommonExceptionSupplier;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

/**
 * @author kaiooliveira
 * created 15/05/2025
 */

@Component
@CommonsLog
public class BancoGetter {

    @Resource
    private BancoRepository repository;

    public Banco byId(Long id) {
        log.info("Consultando um banco a partir do id " + id);
        return repository.findById(id).orElseThrow(CommonExceptionSupplier.naoEncontrado("Banco"));
    }
}
