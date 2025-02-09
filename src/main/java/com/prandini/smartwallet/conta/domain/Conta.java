package com.prandini.smartwallet.conta.domain;

/*
 * @author prandini
 * created 4/5/24
 */

import com.prandini.smartwallet.conta.model.TipoConta;
import com.prandini.smartwallet.lancamento.domain.Lancamento;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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

    @OneToOne(cascade = CascadeType.ALL)
    private ContaBancaria contaAtivos;

    @OneToOne(cascade = CascadeType.ALL)
    private ContaBancaria contaPassivos;

    @Column(name = "DIA_VENCIMENTO")
    private int diaVencimento;

    @Column(name = "DIA_FECHAMENTO")
    private int diaFechamento;

    @Column(name = "SALDO_DISPONIVEL")
    private BigDecimal saldoDisponivel;

    @Column(name = "SALDO_PENDENTE")
    private BigDecimal saldoPendente;

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

    public void addEntrada(BigDecimal valor) {
        this.contaAtivos.setSaldo(this.contaAtivos.getSaldo().add(valor));
    }

    public void addSaida(BigDecimal valor) {
        this.contaPassivos.setSaldo(this.contaPassivos.getSaldo().add(valor));
    }

}
