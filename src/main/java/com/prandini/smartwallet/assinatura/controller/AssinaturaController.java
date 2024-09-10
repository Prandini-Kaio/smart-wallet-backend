package com.prandini.smartwallet.assinatura.controller;

import com.prandini.smartwallet.assinatura.model.AssinaturaFilter;
import com.prandini.smartwallet.assinatura.model.AssinaturaInput;
import com.prandini.smartwallet.assinatura.model.AssinaturaOutput;
import com.prandini.smartwallet.assinatura.service.AssinaturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author kaiooliveira
 * created 04/09/2024
 */

@RestController
@RequestMapping("/assinatura")
@Tag(name = "Assinatura")
public class AssinaturaController {

    @Resource
    private AssinaturaService service;

    @GetMapping
    @Operation(
            summary = "Consulta uma assinatura.",
            description = "Consulta uma assinatura ativa a partir de um filtro."
    )
    public ResponseEntity<List<AssinaturaOutput>> findByFilter(AssinaturaFilter filter){
        return ResponseEntity.ok().body(this.service.byFilter(filter));
    }

    @PostMapping
    @Operation(
            summary = "Cria uma nova assinatura.",
            description = "Cria uma nova assinatura, onde todo mês cadastra um lançamento com o valor da mesma."
    )
    public ResponseEntity<AssinaturaOutput> assinar(@RequestBody AssinaturaInput input){
        return ResponseEntity.ok().body(this.service.criarAssinatura(input));
    }
}
