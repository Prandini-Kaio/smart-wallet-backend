package com.prandini.smartwallet.assinatura.service.actions;

import com.prandini.smartwallet.assinatura.domain.Assinatura;
import com.prandini.smartwallet.assinatura.model.AssinaturaInput;
import com.prandini.smartwallet.assinatura.repository.AssinaturaRepository;
import com.prandini.smartwallet.conta.domain.Conta;
import com.prandini.smartwallet.conta.model.ContaFilter;
import com.prandini.smartwallet.conta.service.actions.ContaGetter;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

/*
 * @author prandini
 * created 9/4/24
 */

@Component
@CommonsLog
public class AssinaturaCreator {

    @Resource
    private AssinaturaRepository repository;

    @Resource
    private ContaGetter contaGetter;

    public Assinatura create(AssinaturaInput input) {

        log.info(String.format("Criando assinatura para %s", input.getCategoria()));

        Conta contaDestino = contaGetter.byId(input.getContaDestinoId());
        Conta contaOrigem = input.getContaOrigemId() != null ? contaGetter.byId(input.getContaDestinoId()) : null;

        Assinatura assinatura = Assinatura.builder()
                .contaDestino(contaDestino)
                .contaOrigem(contaOrigem)
                .categoria(input.getCategoria())
                .tipo(input.getTipo())
                .pagamento(input.getPagamento())
                .valor(input.getValor())
                .dtInicio(input.getDtInicio() != null ? input.getDtInicio() : LocalDate.now())
                .dtFim(input.getDtFim() != null ? input.getDtFim() : LocalDate.now())
                .descricao(input.getDescricao())
                .ativa(input.isAtiva())
                .build();

        return repository.save(assinatura);
    }
}
