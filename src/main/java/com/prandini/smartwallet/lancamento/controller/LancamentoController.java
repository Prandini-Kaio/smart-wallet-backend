package com.prandini.smartwallet.lancamento.controller;

/*
 * @author prandini
 * created 4/16/24
 */

import com.prandini.smartwallet.common.model.ResumoFinanceiro;
import com.prandini.smartwallet.lancamento.model.LancamentoFilter;
import com.prandini.smartwallet.lancamento.model.LancamentoInput;
import com.prandini.smartwallet.lancamento.model.LancamentoOutput;
import com.prandini.smartwallet.common.model.TotalizadorFinanceiro;
import com.prandini.smartwallet.lancamento.service.LancamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/lancamento")
@Tag(name = "Lançamento")
public class LancamentoController {

    @Resource
    private LancamentoService service;

    @GetMapping
    public ResponseEntity<LancamentoOutput> byId(@RequestParam Long id){
        return ResponseEntity.ok().body(service.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<LancamentoOutput>> searchAll(Pageable pageable){
        return ResponseEntity.ok().body(service.findAll(pageable));
    }

    @GetMapping("/totalizador")
    @Operation(description = "Retorna o totalizador dos lançamentos ativos do sistema.")
    public ResponseEntity<TotalizadorFinanceiro> getTotalizador(@RequestParam(required = false) String conta){
        return ResponseEntity.ok().body(this.service.getTotalizador(conta));
    }

    @GetMapping("/totalizador/periodo")
    @Operation(description = "Consulta o totalizador de lançamentos do sistema por periodo e conta. Retorna o total dos lançamentos do periodo, não das transações.")
    public ResponseEntity<TotalizadorFinanceiro> getTotalizador(
            @RequestParam(required = false) String conta,
            @RequestParam(required = false) LocalDate dtInicio,
            @RequestParam(required = false) LocalDate dtFim
    ){
        return ResponseEntity.ok().body(this.service.getTotalizadorByPeriodo(conta, dtInicio, dtFim));
    }

    @GetMapping("/vencimento")
    public ResponseEntity<List<LancamentoOutput>> searchByMes(@RequestParam Integer mes){
        return ResponseEntity.ok().body(service.findByVencimento(mes));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<LancamentoOutput>> searchByMes(LancamentoFilter filter){
        return ResponseEntity.ok().body(service.findByFilter(filter));
    }

    @PostMapping
    @Operation(description = "Criar lançamento com base na data atual.")
    public ResponseEntity<LancamentoOutput> criarLancamento(@RequestBody @Valid LancamentoInput input){
        return ResponseEntity.ok().body(service.criarLancamento(input));
    }
}
