package com.prandini.smartwallet.cartao.domain;

import com.prandini.smartwallet.banco.domain.Banco;
import com.prandini.smartwallet.usuario.domain.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author kaiooliveira
 * created 05/05/2025
 */

@Entity
@Table(name = "CARTAO")
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataVencimento;

    private LocalDate dataFechamento;

    private boolean ativo;

    @ManyToOne
    @JoinColumn(name = "COD_BANCO")
    private Banco banco;

    @ManyToOne
    @JoinColumn(name = "COD_USUARIO")
    private Usuario usuario;
}
