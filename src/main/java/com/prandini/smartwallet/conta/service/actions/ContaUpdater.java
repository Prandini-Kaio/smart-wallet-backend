package com.prandini.smartwallet.conta.service.actions;

/*
 * @author prandini
 * created 4/26/24
 */

import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.conta.model.ContaInput;
import com.prandini.smartwallet.conta.repository.ContaRepository;
import com.prandini.smartwallet.lancamento.domain.TipoLancamentoEnum;
import com.prandini.smartwallet.lancamento.model.LancamentoDeleteEvent;
import com.prandini.smartwallet.lancamento.model.events.LancamentoEvent;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@CommonsLog
public class ContaUpdater {

    @Resource
    private ContaRepository repository;

    @Resource
    private ContaGetter getter;

    @Resource
    private ContaValidator validator;

    public Conta atualizar(ContaInput input){

        log.info(String.format("Atualizando conta com id %s", input.getId()));

        this.validator.validarUpdate(input);

        Conta origin = getter.byId(input.getId());

        origin.setBanco(input.getBanco());
        origin.setNome(input.getNome());
        origin.setDiaVencimento(Integer.parseInt(input.getDiaVencimento()));
        origin.setDiaFechamento(Integer.parseInt(input.getDiaFechamento()));
        origin.setColor(input.getColor());

        return this.repository.save(origin);
    }

    @EventListener
    public void onLancamentoEntry(LancamentoEvent event){
        log.info("Evento de lançamento recebido: " + event.getLancamento().getDescricao());

        Conta contaOrigem = event.getLancamento().getContaOrigem() != null ? event.getLancamento().getContaOrigem() : null;
        Conta contaDestino = event.getLancamento().getContaDestino();

        if(event.getLancamento().getTipoLancamento().equals(TipoLancamentoEnum.ENTRADA))
            contaDestino.addEntrada(event.getLancamento().getValorBruto());

        if(event.getLancamento().getTipoLancamento().equals(TipoLancamentoEnum.SAIDA)){
            contaDestino.addSaida(event.getLancamento().getValorBruto());
        }

        if(contaOrigem != null && event.getLancamento().getTipoLancamento().equals(TipoLancamentoEnum.TRANSFERENCIA)){
            contaOrigem.setSaldoDisponivel(contaOrigem.getSaldoDisponivel().subtract(event.getLancamento().getValorBruto()));
            contaDestino.setSaldoDisponivel(contaDestino.getSaldoDisponivel().add(event.getLancamento().getValorBruto()));
        }

        List<Conta> contas = new ArrayList<>();

        safeAdd(contas, contaOrigem);
        safeAdd(contas, contaDestino);

        this.repository.saveAll(contas);
    }

    @EventListener
    public void onLancamentoDelete(LancamentoDeleteEvent event){
        log.info("Evento de lançamento recebido: " + event.getLancamento().getDescricao());

        TipoLancamentoEnum tipo = event.getLancamento().getTipoLancamento();

        if(tipo.isEntrada()){
            event.getLancamento().getContaDestino().removeEntrada(event.getLancamento().getValorBruto());
        }

        if(tipo.isSaida()){
            event.getLancamento().getContaDestino().removeSaida(event.getLancamento().getValorBruto());
        }

        if(tipo.isTransferencia()){
            event.getLancamento().getContaOrigem().setSaldoDisponivel(event.getLancamento().getContaOrigem().getSaldoDisponivel().add(event.getLancamento().getValorBruto()));
            event.getLancamento().getContaDestino().setSaldoDisponivel(event.getLancamento().getContaDestino().getSaldoDisponivel().subtract(event.getLancamento().getValorBruto()));
        }
    }

    private void safeAdd(List<Conta> list, Conta conta){
        if(conta != null)
            list.add(conta);
    }
}
