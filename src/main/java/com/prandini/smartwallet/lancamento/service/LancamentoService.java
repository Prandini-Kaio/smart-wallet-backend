package com.prandini.smartwallet.lancamento.service;

/*
 * @author prandini
 * created 4/16/24
 */

import com.prandini.smartwallet.common.model.ResumoFinanceiro;
import com.prandini.smartwallet.lancamento.converter.LancamentoConverter;
import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.lancamento.domain.StatusLancamento;
import com.prandini.smartwallet.lancamento.model.LancamentoFilter;
import com.prandini.smartwallet.lancamento.model.LancamentoInput;
import com.prandini.smartwallet.lancamento.model.LancamentoOutput;
import com.prandini.smartwallet.common.model.TotalizadorFinanceiro;
import com.prandini.smartwallet.lancamento.service.actions.LancamentoCreator;
import com.prandini.smartwallet.lancamento.service.actions.LancamentoDeleter;
import com.prandini.smartwallet.lancamento.service.actions.LancamentoGetter;
import com.prandini.smartwallet.lancamento.service.actions.LancamentoUpdater;
import com.prandini.smartwallet.transacao.domain.StatusTransacaoEnum;
import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.service.TransacaoService;
import com.prandini.smartwallet.transacao.service.actions.TransacaoGetter;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@CommonsLog
public class LancamentoService {

    @Resource
    private LancamentoCreator creator;

    @Resource
    private LancamentoGetter getter;

    @Resource
    private LancamentoUpdater updater;

    @Resource
    private LancamentoDeleter deleter;

    @Resource
    private TransacaoService transacaoService;

    @Resource
    private TransacaoGetter transacaoGetter;

    @Resource
    private LancamentoConverter converter;

    @Transactional
    public LancamentoOutput criarLancamento(LancamentoInput input) {
        log.info("Iniciando criação de lancamento.");

        return converter.toOutput(creator.create(input));
    }

    public List<LancamentoOutput> findByFilter(LancamentoFilter filter) {
        log.info(String.format("Iniciando busca de lancamentos por filtro %s.", filter));

        return getter.findByFilter(filter).stream().map(converter::toOutput).toList();
    }

    public TotalizadorFinanceiro getTotalizador(LancamentoFilter filter) {
        return this.getter.getTotalizador(filter);
    }

    public LancamentoOutput findById(Long id) {
        return converter.toOutput(this.getter.byId(id));
    }

    @Transactional
    public void updateStatus(Lancamento lancamento){

        transacaoService.updateStatus(lancamento);

        log.info("Iniciando atualização de status dos lançamentos");

        List<Transacao> transacoes = transacaoGetter.byIdLancamento(lancamento.getId());

        boolean todasQuitadas = transacoes.stream().allMatch(t -> t.getStatus().equals(StatusTransacaoEnum.PAGO));
        boolean algumaVencida = transacoes.stream().anyMatch(t -> t.getStatus().equals(StatusTransacaoEnum.ATRASADO));
        boolean todasCanceladas = transacoes.stream().anyMatch(t -> t.getStatus().equals(StatusTransacaoEnum.CANCELADO));
        boolean algumaEmAberto = transacoes.stream().allMatch(t -> t.getStatus().equals(StatusTransacaoEnum.PENDENTE));

        if(algumaEmAberto)
            lancamento.setStatus(StatusLancamento.EM_ABERTO);
        if(algumaVencida)
            lancamento.setStatus(StatusLancamento.VENCIDO);
        if(todasQuitadas) {
            lancamento.setStatus(StatusLancamento.QUITADO);
        }
        if(todasCanceladas)
            lancamento.setStatus(StatusLancamento.CANCELADO);

        this.updater.update(lancamento);
    }

    @Transactional
    public LancamentoOutput editar(@Valid LancamentoInput input) {
        return converter.toOutput(this.updater.fromInput(input));
    }

    @Transactional
    public void delete(Long id){
        log.info(String.format("Iniciando delete de lancamento por id %s.", id));
        this.deleter.delete(id);
    }

    public LancamentoOutput createMock(LancamentoInput input) {
        return converter.toOutput(this.creator.fromInput(input));
    }

    public ResumoFinanceiro getResumo(LancamentoFilter filter) {
        return this.getter.getResumo(filter);
    }
}
