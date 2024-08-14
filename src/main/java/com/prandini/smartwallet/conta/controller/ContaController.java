package com.prandini.smartwallet.conta.controller;

/*
 * @author prandini
 * created 4/5/24
 */

import com.prandini.smartwallet.common.model.AutcompleteDTO;
import com.prandini.smartwallet.common.model.TotalizadorFinanceiro;
import com.prandini.smartwallet.conta.model.ContaInput;
import com.prandini.smartwallet.conta.model.ContaOutput;
import com.prandini.smartwallet.conta.service.ContaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conta")
@Tag(name = "Conta")
public class ContaController {

    @Resource
    private ContaService service;

    @GetMapping
    @Operation(summary = "Consulta uma conta pelo id")
    public ResponseEntity<ContaOutput> byId(@RequestParam Long id){
        return ResponseEntity.ok().body(this.service.byId(id));
    }

    @GetMapping("/all")
    @Operation(summary = "Retorna todas as contas.")
    public ResponseEntity<List<ContaOutput>> findAll(){
        return ResponseEntity.ok().body(service.getAll());
    }

    @GetMapping("/autocomplete")
    @Operation(summary = "Autocomplete de contas")
    public ResponseEntity<List<AutcompleteDTO>> autocompleteContas(@RequestParam String conta){
        return ResponseEntity.ok().body(this.service.autcompleteContas(conta));
    }

    @GetMapping("/totalizador")
    @Operation(summary = "Consulta o totalizador financeiro através de um filtro.")
    public ResponseEntity<TotalizadorFinanceiro> findTotalizador(@RequestParam(required = false) String filter){
        return ResponseEntity.ok().body(service.getTotalizadorByFilter(filter));
    }

    @PostMapping
    @Operation(summary = "Cria uma conta")
    public ResponseEntity<ContaOutput> create(@RequestBody @Valid ContaInput input){
        return ResponseEntity.ok().body(service.create(input));
    }
}
