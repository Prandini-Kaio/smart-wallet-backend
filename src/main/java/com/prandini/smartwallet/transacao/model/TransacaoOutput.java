package com.prandini.smartwallet.transacao.model;

/*
 * @author prandini
 * created 4/5/24
 */

import com.prandini.smartwallet.transacao.domain.TipoTransacao;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class TransacaoOutput {

    private Long id;

    private Long usuarioId;

    private TipoTransacao tipoTransacao;

    private String valor;

    private String data;

    private String categoria;

    private String formaPagamento;

    private String observacao;

    private Integer numeroParcelas;

    private List<ParcelaOutput> parcelas = new ArrayList<>();
}
