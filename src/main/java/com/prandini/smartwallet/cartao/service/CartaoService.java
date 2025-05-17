package com.prandini.smartwallet.cartao.service;

import com.prandini.smartwallet.cartao.converter.CartaoConverter;
import com.prandini.smartwallet.cartao.domain.Cartao;
import com.prandini.smartwallet.cartao.model.CartaoInput;
import com.prandini.smartwallet.cartao.model.CartaoOutput;
import com.prandini.smartwallet.cartao.service.actions.CartaoCreator;
import com.prandini.smartwallet.cartao.service.actions.CartaoGetter;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kaiooliveira
 * created 15/05/2025
 */

@Service
public class CartaoService {

    @Resource
    private CartaoCreator creator;

    @Resource
    private CartaoGetter getter;

    @Resource
    private CartaoConverter converter;

    public CartaoOutput byId(Long id){
        Cartao cartao = getter.byId(id);
        return converter.toCartaoOutput(cartao);
    }

    public List<CartaoOutput> byUsuarioId(Long userId){
        List<Cartao> cartoes = getter.byUsuarioId(userId);
        return cartoes.stream().map(converter::toCartaoOutput).collect(Collectors.toList());
    }

    public List<CartaoOutput> byBancoId(Long bancoId) {
        return getter.byBancoId(bancoId).stream().map(converter::toCartaoOutput).collect(Collectors.toList());
    }

    public CartaoOutput create(CartaoInput input) {
        Cartao cartaoCreated = creator.createFromInput(input);
        return converter.toCartaoOutput(cartaoCreated);
    }
}
