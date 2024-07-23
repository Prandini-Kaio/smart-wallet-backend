package com.prandini.smartwallet.common.exception;

/**
 * @author kaiooliveira
 * created 10/07/2024
 */
public class CommonExceptionMessages {

    private final static String JA_EXISTENTE = "Já existe um registro de %s com os dados informados.";

    private final static String NAO_ENCONTRADO = "%s não encontrado(a).";

    private final static String CAMPO_OBRIGATORIO = "O campo %s é obrigatório.";

    public static String jaExistente(String entidade){
        return String.format(JA_EXISTENTE, entidade);
    }

    public static String naoEncontrado(String nome){
        return String.format(NAO_ENCONTRADO, nome);
    }

    public static String campoObrigatorio(String campo){
        return String.format(CAMPO_OBRIGATORIO, campo);
    }
}
