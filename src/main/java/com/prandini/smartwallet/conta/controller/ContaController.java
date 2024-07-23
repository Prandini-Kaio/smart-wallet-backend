package com.prandini.smartwallet.conta.controller;

/*
 * @author prandini
 * created 4/5/24
 */

import com.prandini.smartwallet.common.model.AutcompleteDTO;
import com.prandini.smartwallet.conta.model.ContaInput;
import com.prandini.smartwallet.conta.model.ContaOutput;
import com.prandini.smartwallet.conta.service.ContaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
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
@RequestMapping("/conta")
@Tag(name = "Conta")
public class ContaController {

    @Resource
    private ContaService service;

    @GetMapping
    @Operation(summary = "Retorna todas as contas.")
    public ResponseEntity<Page<ContaOutput>> findAll(Pageable pageable){
        return ResponseEntity.ok().body(service.getAll(pageable));
    }

    @GetMapping("/autocomplete")
    @Operation(summary = "Autocomplete de contas")
    public ResponseEntity<List<AutcompleteDTO>> autocompleteContas(@RequestParam String conta){
        return ResponseEntity.ok().body(this.service.autcompleteContas(conta));
    }

    @PostMapping
    @Operation(summary = "Cria uma conta")
    public ResponseEntity<ContaOutput> create(@RequestBody ContaInput input){
        return ResponseEntity.ok().body(service.create(input));
    }
}
