package com.prandini.smartwallet.transacao.service.actions;

import com.prandini.smartwallet.cartao.domain.Cartao;
import com.prandini.smartwallet.cartao.service.actions.CartaoGetter;
import com.prandini.smartwallet.transacao.domain.Parcela;
import com.prandini.smartwallet.transacao.domain.Transacao;
import com.prandini.smartwallet.transacao.model.TransacaoInput;
import com.prandini.smartwallet.transacao.repository.ParcelaRepository;
import com.prandini.smartwallet.transacao.repository.TransacaoRepository;
import com.prandini.smartwallet.usuario.domain.Usuario;
import com.prandini.smartwallet.usuario.service.actions.UsuarioGetter;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Component
@CommonsLog
public class TransacaoCreator {

    @Resource
    private TransacaoRepository repository;

    @Resource
    private CartaoGetter cartaoGetter;

    @Resource
    private UsuarioGetter usuarioGetter;

    @Resource
    private ParcelaRepository parcelaRepository;

    public Transacao fromInput(TransacaoInput input){
        Usuario usuario = usuarioGetter.byId(input.getUsuarioId());
        Cartao cartao = cartaoGetter.byId(input.getCartaoId());

        Transacao transacao = Transacao.builder()
                .usuario(usuario)
                .cartao(cartao)
                .valor(input.getValor())
                .data(input.getData())
                .numeroParcelas(input.getNumeroParcelas())
                .categoria(input.getCategoria())
                .tipo(input.getTipoTransacao())
                .descricao(input.getDescricao())
                .formaPagamento(input.getFormaPagamento())
                .observacao(input.getObservacao())
                .build();

        this.repository.save(transacao);

        if(input.getNumeroParcelas() == null || input.getNumeroParcelas() > 1){
            gerarParcelas(transacao);
        }

        return transacao;
    }

    private void gerarParcelas(Transacao transacao){
        BigDecimal valorParcela = transacao.getValor().divide(
                BigDecimal.valueOf(transacao.getNumeroParcelas()), 2, RoundingMode.HALF_UP
        );

        LocalDate dataBase = transacao.getData();
        Cartao cartao = transacao.getCartao();

        for(int i = 0; i < transacao.getNumeroParcelas(); i++){
            LocalDate vencimento;

            if(cartao != null) {
                vencimento = calcularVencimentoComCartao(dataBase.plusMonths(i), cartao);
            }else {
                vencimento = dataBase.plusMonths(i);
            }

            Parcela parcela = Parcela.builder()
                    .numero(i + 1)
                    .valor(valorParcela)
                    .dataVencimento(vencimento)
                    .transacao(transacao)
                    .cartao(cartao)
                    .build();

            parcelaRepository.save(parcela);
        }
    }

    private LocalDate calcularVencimentoComCartao(LocalDate dataCompra, Cartao cartao){
        LocalDate fechamento = cartao.getDataFechamento();
        LocalDate vencimento = cartao.getDataVencimento();

        if(dataCompra.getDayOfMonth() <= fechamento.getDayOfMonth()){
            return vencimento.withMonth(dataCompra.getMonthValue()).withYear(dataCompra.getYear());
        }else {
            LocalDate proximoMes = fechamento.plusMonths(1);
            return vencimento.withMonth(proximoMes.getMonthValue()).withYear(proximoMes.getYear());
        }
    }

}
