package com.prandini.smartwallet.common.exception;

import lombok.experimental.UtilityClass;

import java.util.function.Supplier;

/**
 * @author kaiooliveira
 * created 10/07/2024
 */

@UtilityClass
public class CommonExceptionSupplier {

    public Supplier<BusinessException> naoEncontrado(String nome){
        return () -> new BusinessException(CommonExceptionMessages.naoEncontrado(nome));
    }

}
