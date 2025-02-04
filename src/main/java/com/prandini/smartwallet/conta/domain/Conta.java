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
import java.time.Month;
import java.time.YearMonth;
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

    @Column(name = "DIA_VENCIMENTO")
    private int diaVencimento;

    @Column(name = "DIA_FECHAMENTO")
    private int diaFechamento;

    @Column(name = "SALDO_PARCIAL")
    private BigDecimal saldoParcial;

    @Column(name = "TIPO_CONTA")
    @Enumerated
    private TipoConta tipoConta;

    @Column(name = "COLOR")
    private String color;

    public String getBancoNome(){
        return banco + " - " + nome;
    }

    public LocalDate getDiaVencimento(YearMonth date) {
        return LocalDate.of(date.getYear(), date.getMonth(), Math.min(diaVencimento, date.atEndOfMonth().getDayOfMonth()));
    }

    public LocalDate getDiaFechamento(YearMonth date) {
        return LocalDate.of(date.getYear(), date.getMonth(), Math.min(diaFechamento, date.atEndOfMonth().getDayOfMonth()));
    }
}
