package com.prandini.smartwallet.banco.controller;

import com.prandini.smartwallet.banco.model.BancoInput;
import com.prandini.smartwallet.banco.model.BancoOutput;
import com.prandini.smartwallet.banco.service.BancoService;
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
@RequestMapping("/banco")
@Tag(name = "Banco")
public class BancoController {

    @Resource
    private BancoService service;

    @GetMapping
    public ResponseEntity<List<BancoOutput>> getAll() {
        return ResponseEntity.ok().body(this.service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BancoOutput> byId(@RequestParam Long id) {
        return ResponseEntity.ok().body(this.service.byId(id));
    }

    @PostMapping
    public ResponseEntity<BancoOutput> create(@RequestBody BancoInput input) {
        return ResponseEntity.ok().body(this.service.create(input));
    }
}
