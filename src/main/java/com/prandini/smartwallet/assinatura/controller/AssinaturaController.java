package com.prandini.smartwallet.assinatura.controller;

import com.prandini.smartwallet.assinatura.model.AssinaturaInput;
import com.prandini.smartwallet.assinatura.model.AssinaturaOutput;
import com.prandini.smartwallet.assinatura.service.AssinaturaService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kaiooliveira
 * created 04/09/2024
 */

@RestController
@RequestMapping("/assinatura")
public class AssinaturaController {

    @Resource
    private AssinaturaService service;

    @PostMapping
    public ResponseEntity<AssinaturaOutput> assinar(AssinaturaInput input){
        return ResponseEntity.ok().body(this.service.criarAssinatura(input));
    }
}
