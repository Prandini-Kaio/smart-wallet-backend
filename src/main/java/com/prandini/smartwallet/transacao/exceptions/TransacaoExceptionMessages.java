package com.prandini.smartwallet.transacao.exceptions;

/**
 * @author kaiooliveira
 * created 24/07/2024
 */
public class TransacaoExceptionMessages {

    private static String PAGAMENTO_INCORRETO = "Pagamento de transação cancelado não é permitido";

    private static String PAGAMENTO_JA_EFETUADO = "A transação %s já foi paga.";

    private static String SEQUENCIA_INVALIDA = "hÁ Uma transação em aberto com vencimento anterior a esta.";


    public static String pagamentoIncorreto(){
        return PAGAMENTO_INCORRETO;
    }

    public static String pagamentoJaEfetuado(String descricao){
        return String.format(PAGAMENTO_JA_EFETUADO, descricao);
    }

    public static String sequenciaInvalida() {
        return SEQUENCIA_INVALIDA;
    }
}
