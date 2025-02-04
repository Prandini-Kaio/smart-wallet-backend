package com.prandini.smartwallet.assinatura.service.actions;

import com.prandini.smartwallet.assinatura.domain.Assinatura;
import com.prandini.smartwallet.assinatura.service.exception.AssinaturaExceptionMessage;
import com.prandini.smartwallet.common.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;

/**
 * @author kaiooliveira
 * created 02/02/2025
 */

@Component
public class AssinaturaValidator {

    public void validarTask(Assinatura assinatura){
        this.validarAtiva(assinatura);
        this.validarFechamentoConta(assinatura);
    }

    private void validarFechamentoConta(Assinatura assinatura) {
        if(!assinatura.getConta().getDiaFechamento(YearMonth.now()).equals(LocalDate.now()))
            throw new BusinessException(AssinaturaExceptionMessage.contaNaoFechada());
    }

    private void validarAtiva(Assinatura assinatura) {
        if (!assinatura.isAtiva()) {
            throw new BusinessException(AssinaturaExceptionMessage.assinaturaInativa());
        }
    }
}
