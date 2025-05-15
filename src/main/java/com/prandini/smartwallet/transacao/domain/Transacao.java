package com.prandini.smartwallet.transacao.domain;

import com.prandini.smartwallet.usuario.domain.Usuario;
import com.prandini.smartwallet.cartao.domain.Cartao;
import com.prandini.smartwallet.recorrencia.domain.Recorrencia;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "COD_USUARIO")
    private Usuario usuario;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Enumerated(EnumType.STRING)
    private TipoTransacao tipo;

    @Column(name = "VALOR", precision = 10, scale = 2)
    private BigDecimal valor;

    private LocalDate data;

    private String categoria;

    private String formaPagamento;

    private String observacao;

    private Integer numeroParcelas;

    @ManyToOne
    @JoinColumn(name = "COD_CARTAO")
    private Cartao cartao;

    @OneToMany(mappedBy = "transacao", orphanRemoval = true)
    private List<Parcela> parcelas = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "COD_RECORRENCIA")
    private Recorrencia recorrencia;
}
