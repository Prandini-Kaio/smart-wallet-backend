package com.prandini.smartwallet.lancamento.controller;

/*
 * @author prandini
 * created 4/16/24
 */

import com.prandini.smartwallet.lancamento.domain.dto.LancamentoFilter;
import com.prandini.smartwallet.lancamento.domain.dto.LancamentoInput;
import com.prandini.smartwallet.lancamento.domain.dto.LancamentoOutput;
import com.prandini.smartwallet.lancamento.service.LancamentoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lancamento")
@Tag(name = "Lançamento")
public class LancamentoController {

    @Resource
    private LancamentoService service;

    @GetMapping
    public ResponseEntity<List<LancamentoOutput>> searchAll(Pageable pageable){
        return ResponseEntity.ok().body(service.findAll(pageable));
    }

    @GetMapping("/vencimento")
    public ResponseEntity<List<LancamentoOutput>> searchByMes(@RequestParam Integer mes){
        return ResponseEntity.ok().body(service.findByVencimento(mes));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<LancamentoOutput>> searchByMes(LancamentoFilter filter){
        return ResponseEntity.ok().body(service.findByFilter(filter));
    }

    @PostMapping
    public ResponseEntity<LancamentoOutput> criarLancamento(@RequestBody @Valid LancamentoInput input){
        return ResponseEntity.ok().body(service.criarLancamento(input));
    }
}
