package com.prandini.smartwallet.lancamento.exceptions;

/*
 * @author prandini
 * created 4/26/24
 */
public class LancamentoException extends RuntimeException {
    public LancamentoException() {
        super();
    }

    public LancamentoException(String message) {
        super(message);
    }

    public LancamentoException(String message, Throwable cause) {
        super(message, cause);
    }

    public LancamentoException(Throwable cause) {
        super(cause);
    }

    protected LancamentoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
