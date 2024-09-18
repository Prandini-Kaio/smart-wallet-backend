package com.prandini.smartwallet.transacao.exceptions;

/**
 * @author kaiooliveira
 * created 24/07/2024
 */
public class TransacaoExceptionMessages {

    private static String PAGAMENTO_INCORRETO = "Pagamento de transação cancelado não é permitido";

    private static String PAGAMENTO_JA_EFETUADO = "A transação já foi paga.";

    private static String SEQUENCIA_INVALIDA = "Há uma transação em aberto com vencimento anterior a esta.";


    public static String pagamentoIncorreto(){
        return PAGAMENTO_INCORRETO;
    }

    public static String pagamentoJaEfetuado(){
        return PAGAMENTO_JA_EFETUADO;
    }

    public static String sequenciaInvalida() {
        return SEQUENCIA_INVALIDA;
    }
}
