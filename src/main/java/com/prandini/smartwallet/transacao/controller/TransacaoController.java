package com.prandini.smartwallet.transacao.controller;

import com.prandini.smartwallet.transacao.converter.TransacaoConverter;
import com.prandini.smartwallet.transacao.domain.dto.TransacaoOutput;
import com.prandini.smartwallet.transacao.repository.TransacaoRepository;
import com.prandini.smartwallet.transacao.service.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @Resource
    private TransacaoRepository repository;

    @GetMapping("/all")
    @Operation(description = "Retorna todas as transações.")
    public ResponseEntity<Page<TransacaoOutput>> searchAll(Pageable pageable){
        return ResponseEntity.ok().body(
                repository.findAll(pageable).map(TransacaoConverter::toOutput)
        );
    }

    @GetMapping
    @Operation(description = "Retorna todas as transações.")
    public ResponseEntity<List<TransacaoOutput>> findByIdLancamento(@RequestParam Long idLancamento){
        return ResponseEntity.ok().body(service.findByIdLancamento(idLancamento));
    }

    @PutMapping("/pagar")
    @Operation(description = "Paga uma transação em aberto.")
    public ResponseEntity<TransacaoOutput> pagarTransacao(@RequestParam Long id){
        return ResponseEntity.ok().body(this.service.pagarTransacao(id));
    }

    @GetMapping("/vencimento")
    @Operation(description = "Retorna todas as transações com base no mês de vencimento.")
    public ResponseEntity<Page<TransacaoOutput>> searchByMonth(@RequestParam Integer month){
        return ResponseEntity.ok().body(this.service.findByMonth(month));
    }

    @GetMapping("/stringFilter")
    @Operation(description = "Retorna todas as transações com base em um filtro.")
    public ResponseEntity<Page<TransacaoOutput>> searchByFilter(@RequestParam String filter){
        return ResponseEntity.ok().body(this.service.findByStringFilter(filter));
    }

}
