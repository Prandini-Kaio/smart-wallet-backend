package com.prandini.smartwallet.conta.domain;

/*
 * @author prandini
 * created 4/5/24
 */

import com.prandini.smartwallet.conta.model.TipoConta;
import com.prandini.smartwallet.lancamento.domain.Lancamento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CONTA")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BANCO")
    private String banco;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "DATA_VENCIMENTO")
    private LocalDate dtVencimento;

    @Column(name = "SALDO_PARCIAL")
    private BigDecimal saldoParcial;

    @Column(name = "TIPO_CONTA")
    @Enumerated
    private TipoConta tipoConta;
}
