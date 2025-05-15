package com.prandini.smartwallet.usuario.service.actions;

import com.prandini.smartwallet.usuario.domain.Usuario;
import com.prandini.smartwallet.usuario.model.UsuarioInput;
import com.prandini.smartwallet.usuario.model.UsuarioOutput;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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

    public UsuarioOutput create(UsuarioInput input) {
        Usuario usuario = creator.createFromInput(input);
        UsuarioOutput output = new UsuarioOutput();
        output.setNome(usuario.getNome());

        return output;
    }
}
