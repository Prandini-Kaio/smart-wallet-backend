package com.prandini.smartwallet.assinatura.controller;

import com.prandini.smartwallet.assinatura.model.AssinaturaInput;
import com.prandini.smartwallet.assinatura.model.AssinaturaOutput;
import com.prandini.smartwallet.assinatura.service.AssinaturaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping
    public ResponseEntity<AssinaturaOutput> assinar(@RequestBody AssinaturaInput input){
        return ResponseEntity.ok().body(this.service.criarAssinatura(input));
    }
}
