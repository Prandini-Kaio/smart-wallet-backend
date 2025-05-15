package com.prandini.smartwallet.cartao.domain;

import com.prandini.smartwallet.usuario.domain.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author kaiooliveira
 * created 05/05/2025
 */

@Entity
@Table(name = "CARTAO")
@Data
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataVencimento;

    private LocalDate dataFechamento;

    private boolean ativo;

    @ManyToOne
    @JoinColumn(name = "COD_USUARIO")
    private Usuario usuario;
}
