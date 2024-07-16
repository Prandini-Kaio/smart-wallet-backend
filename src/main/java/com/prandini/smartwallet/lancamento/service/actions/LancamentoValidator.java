package com.prandini.smartwallet.lancamento.service.actions;

import com.prandini.smartwallet.lancamento.domain.TipoLancamentoEnum;
import com.prandini.smartwallet.lancamento.domain.TipoPagamentoEnum;
import com.prandini.smartwallet.lancamento.domain.dto.LancamentoInput;
import com.prandini.smartwallet.lancamento.exceptions.LancamentoException;
import com.prandini.smartwallet.lancamento.exceptions.LancamentoExceptionMessages;
import org.springframework.stereotype.Component;

/*
 * @author prandini
 * created 4/26/24
 */

@Component
public class LancamentoValidator {


    public void validarCriacao(LancamentoInput input) {
        if(input.getTipoLancamento() == TipoLancamentoEnum.ENTRADA)
            validarEntrada(input);
    }

    private void validarEntrada(LancamentoInput input){
        if(input.getParcelas() > 1)
            throw new LancamentoException(LancamentoExceptionMessages.entradaComParcelas());

        if(input.getTipoPagamento().equals(TipoPagamentoEnum.CREDITO))
            throw new LancamentoException(LancamentoExceptionMessages.entradaCreditoInvalida());
    }
}
