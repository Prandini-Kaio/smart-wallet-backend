package com.prandini.smartwallet.lancamento.controller;

/*
 * @author prandini
 * created 4/16/24
 */

import com.prandini.smartwallet.common.model.ResumoFinanceiroOutput;
import com.prandini.smartwallet.lancamento.model.LancamentoFilter;
import com.prandini.smartwallet.lancamento.model.LancamentoInput;
import com.prandini.smartwallet.lancamento.model.LancamentoOutput;
import com.prandini.smartwallet.common.model.TotalizadorFinanceiro;
import com.prandini.smartwallet.lancamento.model.ResumoFinanceiroFilter;
import com.prandini.smartwallet.lancamento.model.ResumoFinanceiroListOutput;
import com.prandini.smartwallet.lancamento.model.SaldoProjetadoFilter;
import com.prandini.smartwallet.lancamento.model.SaldoProjetadoOutput;
import com.prandini.smartwallet.lancamento.service.LancamentoService;
import com.prandini.smartwallet.lancamento.service.SaldoProjetadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    @Operation(description = "Criar lançamento com base na data atual.")
    public ResponseEntity<LancamentoOutput> criarLancamento(@RequestBody @Valid LancamentoInput input){
        return ResponseEntity.ok().body(service.criarLancamento(input));
    }

    @PostMapping("/create-input")
    @Operation(description = "Cria um input de lançamento com base em um filtro.")
    public ResponseEntity<LancamentoOutput> createInput(@RequestBody LancamentoInput input){
        return ResponseEntity.ok().body(this.service.createMock(input));
    }

    @PostMapping("/byFilter")
    @Operation(description = "Busca lancamentos por filtro")
    public ResponseEntity<List<LancamentoOutput>> byFilter(@RequestBody @Valid LancamentoFilter filter){
        return ResponseEntity.ok().body(service.findByFilter(filter));
    }

    @PutMapping
    @Operation(description = "Editar um lançamento existente")
    public ResponseEntity<LancamentoOutput> editar(@RequestBody @Valid LancamentoInput input){
        return ResponseEntity.ok().body(this.service.editar(input));
    }

    @DeleteMapping
    @Operation(description = "Apaga um lançamento e suas transações.")
    public ResponseEntity<Void> delete(@RequestParam Long id){
        this.service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/resumo")
    @Operation(description = "Retorna o resumo financeiro com base em um filtro.")
    public ResponseEntity<List<ResumoFinanceiroOutput>> getResumo(ResumoFinanceiroFilter filter){
        return ResponseEntity.ok().body(this.saldoProjetadoService.getResumoFinanceiro(filter));
    }
}
