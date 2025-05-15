package com.prandini.smartwallet.usuario.repository;

import com.prandini.smartwallet.usuario.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author kaiooliveira
 * created 05/05/2025
 */

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
