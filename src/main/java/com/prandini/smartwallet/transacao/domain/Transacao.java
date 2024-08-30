package com.prandini.smartwallet.transacao.domain;

import com.prandini.smartwallet.lancamento.domain.Lancamento;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/*
 * @author prandini
 * created 4/5/24
 */
@Entity
@Table(name = "TRANSACAO")
@SequenceGenerator(name = "SEQ_TRNS")
@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Transacao proxima;

    @Column(name = "VALOR")
    private BigDecimal valor;

    @Column(name = "DATA_VENCIMENTO")
    private LocalDateTime dtVencimento;

    @Column(name = "DATA_PAGAMENTO")
    private LocalDateTime dtPagamento;

    @Enumerated
    @Column(name = "STATUS")
    private StatusTransacaoEnum status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "LANCAMENTO_ID")
    private Lancamento lancamento;

    @Column(name = "DESCRICAO")
    private String descricao;
}
