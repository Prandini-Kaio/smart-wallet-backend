package com.prandini.smartwallet.lancamento.domain;

/*
 * @author prandini
 * created 5/3/24
 */
public enum CategoriaLancamentoEnum {

    MORADIA("Moradia"), // GASTOS COM A CASA

    ALIMENTACAO("Alimentação"), // SUPERMERCADO...

    SAUDE("Saúde"), // REMEDIOS, CONSULTAS, ACADEMIA

    CARRO("Carro"), // COMBUSTIVEL, MECANICA, CONSORCIO...

    EDUCACAO("Educação"), // FACULDADE, CURSOS...

    LAZER("Lazer"),

    IMPOSTOS("Impostos"), // IPVA, INSS, IMPOSTO DE RENDA...

    ECONOMIA("Economia"), // INVESTIMENTOS E POUPANCA

    OUTROS("Outros");

    public final String nome;

    CategoriaLancamentoEnum(String nome){
        this.nome = nome;
    }

    @Override
    public String toString(){
        return this.nome;
    }
}
