package com.prandini.smartwallet.cartao.controller;

import com.prandini.smartwallet.cartao.model.CartaoInput;
import com.prandini.smartwallet.cartao.model.CartaoOutput;
import com.prandini.smartwallet.cartao.service.CartaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author kaiooliveira
 * created 15/05/2025
 */

@RestController
@RequestMapping("/cartao")
@Tag(name = "Cartão")
public class CartaoController {

    @Resource
    private CartaoService service;

    @GetMapping
    public ResponseEntity<CartaoOutput> byId(@RequestParam Long id) {
        return ResponseEntity.ok().body(service.byId(id));
    }

    @GetMapping("/banco")
    public ResponseEntity<List<CartaoOutput>> byBancoId(@RequestParam Long bancoId) {
        return ResponseEntity.ok().body(service.byBancoId(bancoId));
    }

    @PostMapping
    public ResponseEntity<CartaoOutput> create(@RequestBody CartaoInput input) {
        return ResponseEntity.ok().body(service.create(input));
    }
}
