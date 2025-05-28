package com.prandini.smartwallet.lancamento.controller;

/*
 * @author prandini
 * created 5/3/24
 */

import com.prandini.smartwallet.lancamento.domain.CategoriaLancamentoEnum;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lancamento/categoria")
@Tag(name = "Lançamento")
public class CategoriaController {

    @GetMapping
    public ResponseEntity<List<CategoriaLancamentoEnum>> getAll(){
        return ResponseEntity.ok().body(List.of(CategoriaLancamentoEnum.values()).stream().sorted(Comparator.comparing(CategoriaLancamentoEnum::getId)).collect(Collectors.toList()));
    }
}
