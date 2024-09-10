package com.prandini.smartwallet.assinatura.domain;

import com.prandini.smartwallet.conta.domain.Conta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


/**
 * @author kaiooliveira
 * created 04/09/2024
 */

@Data
@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
@Table(name = "ASSINATURA")
public class Assinatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Conta conta;

    @Column(name = "VALOR")
    private BigDecimal valor;

    @Column(name = "DATA_INICIO")
    private LocalDate dtInicio;

    @Column(name = "DATA_FIM")
    private LocalDate dtFim;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "ATIVA")
    private boolean ativa;

    @Column(name = "IS_LANCAMENTO")
    private boolean isLancamento;
}
