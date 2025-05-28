package com.prandini.smartwallet.assinatura.service;

import com.prandini.smartwallet.assinatura.domain.Assinatura;
import com.prandini.smartwallet.assinatura.model.AssinaturaInput;
import com.prandini.smartwallet.assinatura.repository.AssinaturaRepository;
import com.prandini.smartwallet.assinatura.service.actions.AssinaturaGetter;
import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.conta.service.actions.ContaGetter;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

/**
 * @author kaiooliveira
 * created 06/02/2025
 */

@Component
@CommonsLog
public class AssinaturaUpdater {

    @Resource
    private AssinaturaGetter getter;

    @Resource
    private AssinaturaRepository repository;

    @Resource
    private ContaGetter contaGetter;

    public Assinatura update(AssinaturaInput input){
        log.info(String.format("Atualizando assinatura %s", input.getDescricao()));

        Assinatura assinatura = getter.byId(input.getId());

        Conta contaDestino = contaGetter.byId(input.getContaDestinoId());
        Conta contaOrigem = input.getContaOrigemId() != null ? contaGetter.byId(input.getContaOrigemId()) : null;

        assinatura.setDescricao(input.getDescricao());
        assinatura.setContaDestino(contaDestino);
        assinatura.setContaOrigem(contaOrigem);
        assinatura.setValor(input.getValor());
        assinatura.setAtiva(input.isAtiva());
        assinatura.setDtInicio(input.getDtInicio());
        assinatura.setDtFim(input.getDtFim());

        return this.repository.save(assinatura);
    }
}
