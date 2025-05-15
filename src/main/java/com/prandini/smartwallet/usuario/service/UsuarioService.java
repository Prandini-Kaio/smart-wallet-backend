package com.prandini.smartwallet.usuario.service;

import com.prandini.smartwallet.usuario.domain.Usuario;
import com.prandini.smartwallet.usuario.model.UsuarioInput;
import com.prandini.smartwallet.usuario.model.UsuarioOutput;
import com.prandini.smartwallet.usuario.service.actions.UsuarioCreator;
import com.prandini.smartwallet.usuario.service.actions.UsuarioGetter;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kaiooliveira
 * created 15/05/2025
 */

@Service
public class UsuarioService {

    @Resource
    private UsuarioCreator creator;

    @Resource
    private UsuarioGetter getter;

    public UsuarioOutput byId(Long id){
        Usuario usuario = this.getter.byId(id);
        UsuarioOutput output = new UsuarioOutput();
        output.setNome(usuario.getNome());

        return output;
    }

    public UsuarioOutput create(UsuarioInput input) {
        Usuario usuario = creator.createFromInput(input);
        UsuarioOutput output = new UsuarioOutput();
        output.setNome(usuario.getNome());

        return output;
    }
}
