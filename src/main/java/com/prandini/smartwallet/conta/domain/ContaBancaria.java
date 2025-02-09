package com.prandini.smartwallet.conta.domain;

import com.prandini.smartwallet.conta.model.TipoConta;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

import java.math.BigDecimal;

/**
 * @author kaiooliveira
 * created 08/02/2025
 */

@Entity
@Table(name = "CONTA_BANCARIA")
@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class ContaBancaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COD_CONTA_BANCARIA")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CONTA_ORIGEM")
    private Conta contaOrigem;

    @Column(name = "SALDO")
    private BigDecimal saldo;

    @Column(name = "TIPO_CONTA")
    @Enumerated(EnumType.STRING)
    private TipoConta tipoConta;

}
