package com.prandini.smartwallet.banco.service;

import com.prandini.smartwallet.banco.converter.BancoConverter;
import com.prandini.smartwallet.banco.domain.Banco;
import com.prandini.smartwallet.banco.model.BancoInput;
import com.prandini.smartwallet.banco.model.BancoOutput;
import com.prandini.smartwallet.banco.service.actions.BancoCreator;
import com.prandini.smartwallet.banco.service.actions.BancoGetter;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kaiooliveira
 * created 15/05/2025
 */

@Service
public class BancoService {

    @Resource
    private BancoCreator creator;

    @Resource
    private BancoGetter getter;

    @Resource
    private BancoConverter converter;

    public BancoOutput byId(Long id) {
        return this.converter.toOutput(this.getter.byId(id));
    }

    public BancoOutput create(BancoInput input) {
        Banco banco = creator.createFromInput(input);
        return converter.toOutput(banco);
    }

    public List<BancoOutput> findAll() {
        return this.getter.findAll().stream().map(converter::toOutput).toList();
    }
}
