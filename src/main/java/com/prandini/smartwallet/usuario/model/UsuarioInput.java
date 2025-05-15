package com.prandini.smartwallet.usuario.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author kaiooliveira
 * created 15/05/2025
 */

@Data
public class UsuarioInput {

    @NotNull(message = "O campo nome é obrigatório.")
    private String nome;

    @NotNull(message = "O campo senha é obrigatório.")
    private String senha;
}
