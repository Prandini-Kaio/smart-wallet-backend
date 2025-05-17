package com.prandini.smartwallet.cartao.service.actions;

import com.prandini.smartwallet.banco.domain.Banco;
import com.prandini.smartwallet.banco.model.BancoInput;
import com.prandini.smartwallet.banco.service.actions.BancoGetter;
import com.prandini.smartwallet.cartao.domain.Cartao;
import com.prandini.smartwallet.cartao.model.CartaoInput;
import com.prandini.smartwallet.cartao.repository.CartaoRepository;
import com.prandini.smartwallet.usuario.domain.Usuario;
import com.prandini.smartwallet.usuario.service.UsuarioService;
import com.prandini.smartwallet.usuario.service.actions.UsuarioGetter;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

/**
 * @author kaiooliveira
 * created 15/05/2025
 */

@Component
@CommonsLog
public class CartaoCreator {

    @Resource
    private CartaoRepository repository;

    @Resource
    private BancoGetter bancoGetter;

    @Resource
    private UsuarioGetter usuarioGetter;

    public Cartao createFromInput(CartaoInput input) {
        Banco banco = bancoGetter.byId(input.getBancoId());
        Usuario usuario = usuarioGetter.byId(input.getUsuarioId());

        Cartao cartao = Cartao.builder()
                .banco(banco)
                .usuario(usuario)
                .nome(input.getNome())
                .dataVencimento(input.getDataVencimento())
                .dataFechamento(input.getDataFechamento())
                .ativo(input.isAtivo())
                .build();

        return this.repository.save(cartao);
    }
}
