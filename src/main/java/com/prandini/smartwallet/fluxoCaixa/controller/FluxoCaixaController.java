package com.prandini.smartwallet.fluxoCaixa.controller;

import com.prandini.smartwallet.fluxoCaixa.model.FluxoCaixaProjetadoFilter;
import com.prandini.smartwallet.fluxoCaixa.model.FluxoCaixaProjetadoOutput;
import com.prandini.smartwallet.fluxoCaixa.service.FluxoCaixaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kaiooliveira
 * created 04/01/2025
 */

@RestController
@RequestMapping("fluxo-caixa")
public class FluxoCaixaController {

    @Resource
    private FluxoCaixaService service;

    @GetMapping("/projetado")
    @Operation(summary = "Traz o relatorio de fluxo de caixa projetado de acordo com o filtro.")
    public ResponseEntity<FluxoCaixaProjetadoOutput> getProjetadoByFiltro(FluxoCaixaProjetadoFilter filter){
        return ResponseEntity.ok(service.getProjetadoByFilter(filter));
    }
}
