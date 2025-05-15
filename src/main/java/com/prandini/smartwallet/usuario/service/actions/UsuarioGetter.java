package com.prandini.smartwallet.usuario.service.actions;

import com.prandini.smartwallet.common.exception.CommonExceptionSupplier;
import com.prandini.smartwallet.usuario.domain.Usuario;
import com.prandini.smartwallet.usuario.repository.UsuarioRepository;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

/**
 * @author kaiooliveira
 * created 05/05/2025
 */

@Component
@CommonsLog
public class UsuarioGetter {

    @Resource
    private UsuarioRepository repository;

    public Usuario byId(Long id){
        log.info("Buscando usuario pelo id " + id);

        return repository.findById(id).orElseThrow(CommonExceptionSupplier.naoEncontrado("Usuário"));
    }
}
