package com.prandini.smartwallet.assinatura.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kaiooliveira
 * created 04/09/2024
 */

@RestController
@RequestMapping("/assinatura")
public class AssinaturaController {

    public ResponseEntity<Void> assinar(){
        return ResponseEntity.ok().build();
    }
}
