package com.prandini.smartwallet.banco.domain;

import com.prandini.smartwallet.cartao.domain.Cartao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kaiooliveira
 * created 15/05/2025
 */

@Entity
@Table(name = "BANCO")
@Data
public class Banco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME")
    private String nome;

    @OneToMany(mappedBy = "banco")
    private List<Cartao> cartoes = new ArrayList<>();
}
