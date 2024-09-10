package com.prandini.smartwallet.transacao.controller;

import com.prandini.smartwallet.common.model.TotalizadorFinanceiro;
import com.prandini.smartwallet.transacao.converter.TransacaoConverter;
import com.prandini.smartwallet.transacao.domain.dto.TransacaoOutput;
import com.prandini.smartwallet.transacao.model.TransacaoFilter;
import com.prandini.smartwallet.transacao.repository.TransacaoRepository;
import com.prandini.smartwallet.transacao.service.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping
    public ResponseEntity<List<TransacaoOutput>> findByFilter(
            TransacaoFilter filter
    ){
        return ResponseEntity.ok().body(this.service.findByFilter(filter));
    }

    @GetMapping("/all")
    @Operation(description = "Retorna todas as transações.")
    public ResponseEntity<Page<TransacaoOutput>> searchAll(Pageable pageable){
        return ResponseEntity.ok().body(
                repository.findAll(pageable).map(TransacaoConverter::toOutput)
        );
    }

    @GetMapping("totalizador")
    @Operation
    public ResponseEntity<TotalizadorFinanceiro> searchTotalizador(TransacaoFilter filter){
        return ResponseEntity.ok().body(this.service.findTotalizadorByFilter(filter));
    }

    @GetMapping("totalizador/periodo")
    @Operation(description = "Consulta o totalizador de transações do sistema por periodo e conta. Retorna o total das transações do periodo.")
    public ResponseEntity<TotalizadorFinanceiro> getTotalizadorByPeriodo(
            @RequestParam(required = false) String conta,
            @RequestParam(required = false) LocalDate dtInicio,
            @RequestParam(required = false) LocalDate dtFim
    ){
        return ResponseEntity.ok().body(service.findTotalizadorFinanceiro(conta, dtInicio, dtFim));
    }

    @PutMapping("/pagar")
    @Operation(description = "Paga uma transação em aberto.")
    public ResponseEntity<TransacaoOutput> pagarTransacao(@RequestParam Long id){
        return ResponseEntity.ok().body(this.service.pagarTransacao(id));
    }


}
