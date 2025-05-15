package com.prandini.smartwallet.usuario.service.actions;

import com.prandini.smartwallet.usuario.domain.Usuario;
import com.prandini.smartwallet.usuario.model.UsuarioInput;
import com.prandini.smartwallet.usuario.repository.UsuarioRepository;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author kaiooliveira
 * created 15/05/2025
 */

@Component
@CommonsLog
public class UsuarioCreator {

    @Resource
    private UsuarioRepository repository;

    public Usuario createFromInput(UsuarioInput input){
        log.info("Criando novo usuario a partir do input: " + input);

        Usuario usuario = Usuario.builder()
                .nome(input.getNome())
                .senhaHash(input.getSenha())
                .cartoes(new ArrayList<>())
                .transacoes(new ArrayList<>())
                .build();

        return this.repository.save(usuario);
    }
}
