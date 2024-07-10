package com.prandini.smartwallet.transacao.controller;

/*
 * @author prandini
 * created 5/4/24
 */

import com.prandini.smartwallet.transacao.domain.TransacaoStatusEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transacao/status")
public class StatusTransacaoController {

    @GetMapping
    public ResponseEntity<List<TransacaoStatusEnum>> getStatus(){
        return ResponseEntity.ok().body(List.of(TransacaoStatusEnum.values()));
    }
}
