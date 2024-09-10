package com.prandini.smartwallet.lancamento.service.actions;

import com.prandini.smartwallet.common.exception.BusinessException;
import com.prandini.smartwallet.common.exception.CommonExceptionMessages;
import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.conta.model.TipoConta;
import com.prandini.smartwallet.conta.service.actions.ContaGetter;
import com.prandini.smartwallet.lancamento.domain.CategoriaLancamentoEnum;
import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.lancamento.domain.TipoLancamentoEnum;
import com.prandini.smartwallet.lancamento.domain.TipoPagamentoEnum;
import com.prandini.smartwallet.lancamento.model.LancamentoInput;
import com.prandini.smartwallet.lancamento.exceptions.LancamentoException;
import com.prandini.smartwallet.lancamento.exceptions.LancamentoExceptionMessages;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

/*
 * @author prandini
 * created 4/26/24
 */

@Component
@CommonsLog
public class LancamentoValidator {


    @Resource
    private ContaGetter contaGetter;

    public void validarUpdate(Lancamento lancamento) {
        log.info("Implementar validacao de update do lancamento");
    }

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

        this.validarLancamentoEconomia(input);
        this.validarLancamentoInvestimento(input);
    }

    private void validarLancamentoEconomia(LancamentoInput input){
        if(input.getCategoriaLancamento().equals(CategoriaLancamentoEnum.ECONOMIA)){
            Conta conta = contaGetter.getContaByFilter(input.getConta());

            if(!conta.getTipoConta().equals(TipoConta.ECONOMIA)){
                throw new BusinessException(LancamentoExceptionMessages.contaIncorreta("economia", "Economias"));
            }
        }
    }

    private void validarLancamentoInvestimento(LancamentoInput input){
        if(input.getCategoriaLancamento().equals(CategoriaLancamentoEnum.INVESTIMENTO)){
            Conta conta = contaGetter.getContaByFilter(input.getConta());

            if(!conta.getTipoConta().equals(TipoConta.INVESTIMENTO)){
                throw new BusinessException(LancamentoExceptionMessages.contaIncorreta("investimento", "Investimentos"));
            }
        }
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
