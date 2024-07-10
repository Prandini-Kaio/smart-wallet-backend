package com.prandini.smartwallet.lancamento.domain.dto;

import com.prandini.smartwallet.lancamento.domain.TipoLancamentoEnum;
import lombok.Getter;
import lombok.Setter;

/*
 * @author prandini
 * created 4/29/24
 */

@Getter @Setter
public class LancamentoFilter {

    private Integer mes;

    private TipoLancamentoEnum tipoLancamento;
}
