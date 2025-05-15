package com.prandini.smartwallet;

import com.prandini.smartwallet.cartao.domain.Cartao;
import com.prandini.smartwallet.transacao.domain.Transacao;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

/**
 * @author kaiooliveira
 * created 05/05/2025
 */

@Entity
@Table(name = "USUARIO")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String senhaHash;

    @OneToMany(mappedBy = "USUARIO", orphanRemoval = true)
    private List<Cartao> cartoes;

    @OneToMany(mappedBy = "USUARIO", orphanRemoval = true)
    private List<Transacao> transacoes;
}
