package com.prandini.smartwallet.lancamento.domain;

/*
 * @author prandini
 * created 5/3/24
 */

import lombok.Getter;

@Getter
public enum CategoriaLancamentoEnum {

    ALIMENTACAO(1, "Alimentação", "food"),

    MORADIA(2, "Moradia", "home"),

    LAZER(3, "Lazer", "beach"),

    CACHORRO(4, "Cachorro", "dog-side"),

    CARRO(5, "Carro", "car"),

    SAUDE(6, "Saúde", "heart-multiple"),

    RENDA(7, "Renda", "cash"),

    EDUCACAO(8, "Educação", "school"),

    IMPOSTOS(9, "Impostos", "knife"),

    INVESTIMENTO(10, "Investimento", "piggy-bank"),

    ECONOMIA(11, "Economia", "piggy-bank"),


    ASSINATURA(12, "Assinatura", "draw-pen"),

    OUTROS(13, "Outros", "cube");

    public final Integer id;

    public final String nome;

    public String icone;

    CategoriaLancamentoEnum(Integer id, String nome, String icone){
        this.id = id;
        this.nome = nome;
        this.icone = icone;
    }

    @Override
    public String toString(){
        return this.nome;
    }
}
