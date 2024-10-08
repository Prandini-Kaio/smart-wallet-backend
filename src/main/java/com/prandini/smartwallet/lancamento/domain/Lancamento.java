package com.prandini.smartwallet.lancamento.domain;

/*
 * @author prandini
 * created 4/5/24
 */

import com.prandini.smartwallet.conta.domain.Conta;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "LANCAMENTO")
@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Data
public class Lancamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TIPO")
    @Enumerated
    private TipoLancamentoEnum tipoLancamento;

    @Column(name = "CATEGORIA")
    @Enumerated
    private CategoriaLancamentoEnum categoriaLancamento;

    @Column(name = "TIPO_PAGAMENTO")
    private TipoPagamentoEnum tipoPagamento;

    @Column(name = "STATUS")
    private StatusLancamento status;

    @Column(name = "VALOR")
    private BigDecimal valor;

    @Column(name = "DATA_CRIACAO")
    private LocalDateTime dtCriacao;

    @Column(name = "DATA_STATUS")
    private LocalDateTime dtAlteracaoStatus;

    @Column(name = "PARCELAS")
    private int parcelas;

    @ManyToOne
    private Conta conta;

    @Column(name = "DESCRICAO")
    private String descricao;

}
