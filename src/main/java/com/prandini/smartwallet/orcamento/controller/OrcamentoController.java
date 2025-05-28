package com.prandini.smartwallet.orcamento.controller;

import com.prandini.smartwallet.orcamento.model.OrcamentoFilter;
import com.prandini.smartwallet.orcamento.model.OrcamentoInput;
import com.prandini.smartwallet.orcamento.model.OrcamentoOutput;
import com.prandini.smartwallet.orcamento.service.OrcamentoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Month;
import java.util.List;

/**
 * @author kaiooliveira
 * created 27/12/2024
 */

@RestController
@RequestMapping("/orcamento")
public class OrcamentoController {

    @Resource
    private OrcamentoService orcamentoService;

    @GetMapping
    @Operation(summary = "Listar orçamentos", description = "Lista todos os orçamentos cadastrados")
    public ResponseEntity<List<OrcamentoOutput>> listarOrcamentos(@RequestParam Month mes) {
        return ResponseEntity.ok().body(this.orcamentoService.listarOrcamentos(mes));
    }

    @PostMapping
    @Operation(summary = "Criar orçamento", description = "Cria um novo orçamento")
    public ResponseEntity<OrcamentoOutput> criarOrcamento(@RequestBody OrcamentoInput input) {
        return ResponseEntity.ok().body(this.orcamentoService.criarOrcamento(input));
    }

    @PutMapping
    @Operation(summary = "Atualizar orçamento", description = "Atualiza um orçamento existente")
    public ResponseEntity<OrcamentoOutput> atualizarOrcamento(@RequestBody OrcamentoInput input) {
        return ResponseEntity.ok().body(this.orcamentoService.atualizarOrcamento(input));
    }

    @DeleteMapping
    @Operation(summary = "Deletar orçamento", description = "Deleta um orçamento existente")
    public ResponseEntity<Void> deletarOrcamento(@RequestParam Long id) {
        this.orcamentoService.delete(id);
        return ResponseEntity.ok().build();
    }
}
