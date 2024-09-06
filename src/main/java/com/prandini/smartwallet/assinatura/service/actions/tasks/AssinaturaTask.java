package com.prandini.smartwallet.assinatura.service.actions.tasks;

/*
 * @author prandini
 * created 9/4/24
 */

import com.prandini.smartwallet.assinatura.domain.Assinatura;
import com.prandini.smartwallet.assinatura.model.AssinaturaFilter;
import com.prandini.smartwallet.assinatura.service.actions.AssinaturaGetter;
import com.prandini.smartwallet.lancamento.domain.CategoriaLancamentoEnum;
import com.prandini.smartwallet.lancamento.domain.StatusLancamento;
import com.prandini.smartwallet.lancamento.domain.TipoLancamentoEnum;
import com.prandini.smartwallet.lancamento.domain.TipoPagamentoEnum;
import com.prandini.smartwallet.lancamento.model.LancamentoInput;
import com.prandini.smartwallet.lancamento.service.LancamentoService;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@CommonsLog
public class AssinaturaTask {

    @Resource
    private AssinaturaGetter getter;

    @Resource
    private LancamentoService lancamentoService;

    @Scheduled(cron = "0 0 0 1 * ?")
    public void cadastrarLancamento(){
        log.info("Renovando assinaturas");

        List<Assinatura> assinaturas = getter.streamByFilter(AssinaturaFilter.builder().ativa(true).build());

        if (!assinaturas.isEmpty()) {
            assinaturas.forEach(a -> {
                log.info(String.format("Iniciando renovação para %s", a.getDescricao()));
                LancamentoInput input = LancamentoInput.builder()
                        .tipoLancamento(TipoLancamentoEnum.SAIDA)
                        .categoriaLancamento(CategoriaLancamentoEnum.ASSINATURA)
                        .tipoPagamento(TipoPagamentoEnum.CREDITO)
                        .status(StatusLancamento.EM_ABERTO)
                        .valor(a.getValor())
                        .dtCriacao(LocalDateTime.now())
                        .parcelas(1)
                        .conta(a.getConta().getBanco())
                        .descricao(a.getDescricao())
                        .build();

                lancamentoService.criarLancamento(input);
            });
        }
    }
}
