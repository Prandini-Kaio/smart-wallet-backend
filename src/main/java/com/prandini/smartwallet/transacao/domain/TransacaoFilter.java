package com.prandini.smartwallet.transacao.domain;

/*
 * @author prandini
 * created 5/4/24
 */

import lombok.Data;

@Data
public class TransacaoFilter {

    private Integer mes;

    private TransacaoStatusEnum status;
}
