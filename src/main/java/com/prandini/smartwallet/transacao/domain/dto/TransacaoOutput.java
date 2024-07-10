package com.prandini.smartwallet.transacao.domain.dto;

/*
 * @author prandini
 * created 4/5/24
 */

import com.prandini.smartwallet.transacao.domain.TransacaoStatusEnum;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class TransacaoOutput {
    private Long id;

    private BigDecimal valor;

    private TransacaoStatusEnum status;

    private String dtVencimento;

    private String descricao;
}
