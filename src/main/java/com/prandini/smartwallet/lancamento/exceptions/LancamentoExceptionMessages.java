package com.prandini.smartwallet.lancamento.exceptions;

/*
 * @author prandini
 * created 4/26/24
 */
public class LancamentoExceptionMessages {
    private static String ENTRADA_COM_PARCELAS = "Lancamento de entrada não deve conter parcelas.";

    private static String ENTRADA_CREDITO_INVALIDA = "Um lançamento de entrada não pode ser por crédito.";

    private static String DEBITO_COM_PARCELAS = "Um débito não contém parcelas.";

    private static String CONTA_INCORRETA = "Lançamento de %s deve ser registrado apenas na conta de %s.";

    public static String entradaComParcelas(){
        return ENTRADA_COM_PARCELAS;
    }

    public static String entradaCreditoInvalida(){
        return ENTRADA_CREDITO_INVALIDA;
    }

    public static String debitoComParcelas() {
        return DEBITO_COM_PARCELAS;
    }

    public static String contaIncorreta(String tipo, String conta) {
        return String.format(CONTA_INCORRETA, tipo, conta);
    }
}
