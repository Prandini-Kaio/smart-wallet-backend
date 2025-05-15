package com.prandini.smartwallet.transacao.domain;

import com.prandini.smartwallet.cartao.domain.Cartao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author kaiooliveira
 * created 05/05/2025
 */

@Entity
@Table(name = "PARCELA")
@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Parcela {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NUMERO")
    private Integer numero;

    @Column(name = "VALOR", precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(name = "DATA_VENCIMENTO")
    private LocalDate dataVencimento;

    @Column(name = "PAGA")
    private Boolean paga = false;

    @ManyToOne
    @JoinColumn(name = "COD_CARTAO")
    private Cartao cartao;

    @ManyToOne
    @JoinColumn(name = "COD_TRANSACAO")
    private Transacao transacao;
}
