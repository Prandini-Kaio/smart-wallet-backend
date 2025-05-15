package com.prandini.smartwallet.cartao.service.actions;

import com.prandini.smartwallet.cartao.domain.Cartao;
import com.prandini.smartwallet.cartao.repository.CartaoRepository;
import com.prandini.smartwallet.common.exception.CommonExceptionSupplier;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author kaiooliveira
 * created 05/05/2025
 */

@Component
public class CartaoGetter {

    @Resource
    private CartaoRepository repository;

    public Cartao byId(Long id){
        return repository.findById(id).orElseThrow(CommonExceptionSupplier.naoEncontrado("Cartão"));
    }

    public List<Cartao> byBancoId(Long bancoId) {
        return this.repository.findAllByBancoId(bancoId);
    }
}
