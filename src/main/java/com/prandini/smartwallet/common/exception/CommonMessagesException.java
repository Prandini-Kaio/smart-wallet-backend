package com.prandini.smartwallet.common.exception;

/**
 * @author kaiooliveira
 * created 10/07/2024
 */
public class CommonMessagesException {

    private static String JA_EXISTENTE = "Já existe um registro de %s com os dados informados.";

    public static String jaExistente(String entidade){
        return String.format(JA_EXISTENTE, entidade);
    }
}
