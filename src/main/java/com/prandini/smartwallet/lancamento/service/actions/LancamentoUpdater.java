package com.prandini.smartwallet.lancamento.service.actions;

import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.lancamento.domain.StatusLancamento;
import com.prandini.smartwallet.lancamento.model.LancamentoInput;
import com.prandini.smartwallet.lancamento.repository.LancamentoRepository;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author kaiooliveira
 * created 06/08/2024
 */

@Component
@CommonsLog
public class LancamentoUpdater {

    @Resource
    private LancamentoRepository repository;

    @Resource
    private LancamentoGetter getter;

    @Resource
    private LancamentoValidator validator;

    public Lancamento quitarLancamento(Long id){

        log.info(String.format("Alterando status do lancamento %s para QUITADO", id));

        Lancamento lancamento = getter.byId(id);

        lancamento.setStatus(StatusLancamento.QUITADO);
        lancamento.setDtAlteracaoStatus(LocalDateTime.now());

        return repository.save(lancamento);
    }

    public Lancamento update(Lancamento lancamento){

        log.info(String.format("Alterando lancamento %s", lancamento.getId()));

        this.validator.validarUpdate(lancamento);

        Lancamento origin = getter.byId(lancamento.getId());

        origin.setTipoLancamento(lancamento.getTipoLancamento());
        origin.setCategoriaLancamento(lancamento.getCategoriaLancamento());
        origin.setTipoPagamento(lancamento.getTipoPagamento());
        origin.setStatus(lancamento.getStatus());
        origin.setValor(lancamento.getValor());
        origin.setDtCriacao(lancamento.getDtCriacao());
        origin.setParcelas(lancamento.getParcelas());
        origin.setConta(lancamento.getConta());
        origin.setDescricao(lancamento.getDescricao());

        return repository.save(origin);
    }
}
