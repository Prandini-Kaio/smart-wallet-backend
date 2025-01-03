package com.prandini.smartwallet.conta.service.actions;

/*
 * @author prandini
 * created 4/26/24
 */

import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.conta.model.ContaInput;
import com.prandini.smartwallet.conta.repository.ContaRepository;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

@Component
@CommonsLog
public class ContaUpdater {

    @Resource
    private ContaRepository repository;

    @Resource
    private ContaGetter getter;

    @Resource
    private ContaValidator validator;

    public Conta atualizar(ContaInput input){

        log.info(String.format("Atualizando conta com id %s", input.getId()));

        this.validator.validarUpdate(input);

        Conta origin = getter.byid(input.getId());

        origin.setBanco(input.getBanco());
        origin.setNome(input.getNome());
        origin.setTipoConta(input.getTipoConta());
        origin.setDiaVencimento(Integer.parseInt(input.getDiaVencimento()));
        origin.setDiaFechamento(Integer.parseInt(input.getDiaFechamento()));
        origin.setColor(input.getColor());

        return this.repository.save(origin);
    }
}
