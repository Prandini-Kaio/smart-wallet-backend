package com.prandini.smartwallet.lancamento.domain;

/*
 * @author prandini
 * created 4/5/24
 */

import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.transacao.domain.Transacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lancamento")
    private List<Transacao> transacoes;

    @ManyToOne
    private Conta conta;

    @Column(name = "DESCRICAO")
    private String descricao;

}
