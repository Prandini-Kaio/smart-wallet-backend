package com.prandini.smartwallet.lancamento.service.actions;

import com.prandini.smartwallet.common.exception.BusinessException;
import com.prandini.smartwallet.common.exception.CommonExceptionMessages;
import com.prandini.smartwallet.common.exception.CommonExceptionSupplier;
import com.prandini.smartwallet.lancamento.domain.CategoriaLancamentoEnum;
import com.prandini.smartwallet.lancamento.domain.TipoLancamentoEnum;
import com.prandini.smartwallet.lancamento.domain.TipoPagamentoEnum;
import com.prandini.smartwallet.lancamento.model.LancamentoInput;
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
        this.validarInput(input);
    }

    private void validarInput(LancamentoInput input){
        this.validarTipoPagamento(input);
        this.validarCategoriaLancamento(input);
        this.validarTipoLancamento(input);
    }

    private void validarTipoPagamento(LancamentoInput input){
        if(input.getTipoPagamento() == null)
            throw new BusinessException(CommonExceptionMessages.campoObrigatorio("Tipo pagamento"));

        if(input.getTipoPagamento().equals(TipoPagamentoEnum.DEBITO) && input.getParcelas() > 1)
            throw new BusinessException(LancamentoExceptionMessages.debitoComParcelas());
    }

    private void validarCategoriaLancamento(LancamentoInput input){
        if(input.getCategoriaLancamento() == null)
            throw new BusinessException(CommonExceptionMessages.campoObrigatorio("Categoria lançamento"));
    }

    private void validarTipoLancamento(LancamentoInput input){
        if(input.getTipoLancamento() == null)
            throw new BusinessException(CommonExceptionMessages.campoObrigatorio("Tipo lançamento"));

        if(input.getTipoLancamento().equals(TipoLancamentoEnum.ENTRADA))
            validarEntrada(input);
    }

    private void validarEntrada(LancamentoInput input){
        if(input.getParcelas() > 1)
            throw new LancamentoException(LancamentoExceptionMessages.entradaComParcelas());

        if(input.getTipoPagamento().equals(TipoPagamentoEnum.CREDITO))
            throw new LancamentoException(LancamentoExceptionMessages.entradaCreditoInvalida());
    }
}
