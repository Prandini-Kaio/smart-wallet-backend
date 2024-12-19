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
import com.prandini.smartwallet.lancamento.model.SaldoProjetadoOutput;
import com.prandini.smartwallet.lancamento.service.LancamentoService;
import com.prandini.smartwallet.lancamento.service.SaldoProjetadoService;
import com.prandini.smartwallet.transacao.model.TransacaoFilter;
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

    @Resource
    private SaldoProjetadoService saldoProjetadoService;

    @GetMapping
    public ResponseEntity<List<LancamentoOutput>> getByFilter(LancamentoFilter filter){
        return ResponseEntity.ok().body(service.findByFilter(filter));
    }

    @GetMapping("/id")
    public ResponseEntity<LancamentoOutput> byId(@RequestParam Long id){
        return ResponseEntity.ok().body(service.findById(id));
    }

    @GetMapping("/totalizador")
    @Operation(description = "Retorna o totalizador dos lançamentos ativos do sistema.")
    public ResponseEntity<TotalizadorFinanceiro> getTotalizador(LancamentoFilter filter){
        return ResponseEntity.ok().body(this.service.getTotalizador(filter));
    }

    @GetMapping("/saldo-projetado/lancamento")
    @Operation(description = "Calcula o saldo projetado com base em um filtro.")
    public ResponseEntity<List<SaldoProjetadoOutput>> getSaldoProjetado(LancamentoFilter filter){
        return ResponseEntity.ok().body(this.saldoProjetadoService.getSaldoProjetado(filter));
    }

    @GetMapping("/saldo-projetado/transacao")
    @Operation(description = "Calcula o saldo projetado com base em um filtro.")
    public ResponseEntity<List<SaldoProjetadoOutput>> getSaldoProjetado(TransacaoFilter filter){
        return ResponseEntity.ok().body(this.saldoProjetadoService.getSaldoProjetado(filter));
    }

    @PostMapping
    @Operation(description = "Criar lançamento com base na data atual.")
    public ResponseEntity<LancamentoOutput> criarLancamento(@RequestBody @Valid LancamentoInput input){
        return ResponseEntity.ok().body(service.criarLancamento(input));
    }
}
