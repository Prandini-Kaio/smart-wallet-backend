package com.prandini.smartwallet.usuario.controller;

import com.prandini.smartwallet.usuario.model.UsuarioInput;
import com.prandini.smartwallet.usuario.model.UsuarioOutput;
import com.prandini.smartwallet.usuario.service.UsuarioService;
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
@RequestMapping("/usuario")
@Tag(name = "Usuario")
public class UsuarioController {

    @Resource
    private UsuarioService service;

    @GetMapping
    public ResponseEntity<UsuarioOutput> byId(@RequestParam Long id) {
        return ResponseEntity.ok().body(this.service.byId(id));
    }

    @PostMapping
    public ResponseEntity<UsuarioOutput> create(@RequestBody UsuarioInput input) {
        return ResponseEntity.ok().body(this.service.create(input));
    }
}
