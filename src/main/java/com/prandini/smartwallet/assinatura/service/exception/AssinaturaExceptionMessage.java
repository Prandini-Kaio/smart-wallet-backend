package com.prandini.smartwallet.assinatura.service.exception;

/**
 * @author kaiooliveira
 * created 02/02/2025
 */
public class AssinaturaExceptionMessage {

    public static String ASSINATURA_INATIVA = "A assinatura está inativa.";

    public static String CONTA_NAO_FECHADA = "A conta não foi fechada.";

    public static String assinaturaInativa(){
        return ASSINATURA_INATIVA;
    }

    public static String contaNaoFechada() {
        return CONTA_NAO_FECHADA;
    }
}
