package com.prandini.smartwallet.usuario.domain;

import com.prandini.smartwallet.cartao.domain.Cartao;
import com.prandini.smartwallet.transacao.domain.Transacao;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author kaiooliveira
 * created 05/05/2025
 */

@Entity
@Table(name = "USUARIO")
@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String senhaHash;

    @OneToMany(mappedBy = "usuario", orphanRemoval = true)
    private List<Cartao> cartoes;

    @OneToMany(mappedBy = "usuario", orphanRemoval = true)
    private List<Transacao> transacoes;
}
