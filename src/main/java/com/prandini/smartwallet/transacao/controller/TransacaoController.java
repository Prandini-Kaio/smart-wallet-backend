package com.prandini.smartwallet.transacao.controller;

import com.prandini.smartwallet.transacao.model.TransacaoInput;
import com.prandini.smartwallet.transacao.model.TransacaoOutput;
import com.prandini.smartwallet.transacao.service.TransacaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * @author prandini
 * created 4/17/24
 */
@RestController
@RequestMapping("/transacao")
@Tag(name = "Transação")
public class TransacaoController {

    @Resource
    private TransacaoService service;

    @GetMapping
    public ResponseEntity<List<TransacaoOutput>> getAll(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @PostMapping
    public ResponseEntity<TransacaoOutput> create(@RequestBody TransacaoInput input) {
        return ResponseEntity.ok().body(service.createWithTransactional(input));
    }
}
