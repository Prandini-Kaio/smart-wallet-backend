package com.prandini.smartwallet.lancamento.domain;

/*
 * @author prandini
 * created 5/3/24
 */
public enum CategoriaLancamentoEnum {

    MORADIA("Moradia", "home"), // GASTOS COM A CASA

    ALIMENTACAO("Alimentação", "food"), // SUPERMERCADO...

    SAUDE("Saúde", "heart-multiple"), // REMEDIOS, CONSULTAS, ACADEMIA

    CARRO("Carro", "car"), // COMBUSTIVEL, MECANICA, CONSORCIO...

    EDUCACAO("Educação", "school"), // FACULDADE, CURSOS...

    LAZER("Lazer", "beach"),

    IMPOSTOS("Impostos", "knife"), // IPVA, INSS, IMPOSTO DE RENDA...

    ECONOMIA("Economia", "piggy-bank"), // INVESTIMENTOS E POUPANCA

    INVESTIMENTO("Investimento", "piggy-bank"), // INVESTIMENTOS E POUPANCA

    CACHORRO("Cachorro", "dog-side"),

    OUTROS("Outros", "cube"),

    RENDA("Renda", "cash");

    public final String nome;

    public String icone;

    CategoriaLancamentoEnum(String nome, String icone){
        this.nome = nome;
        this.icone = icone;
    }

    @Override
    public String toString(){
        return this.nome;
    }
}
