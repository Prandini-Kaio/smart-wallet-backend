package com.prandini.smartwallet.lancamento.service.actions;

/*
 * @author prandini
 * created 12/25/24
 */

import com.prandini.smartwallet.lancamento.domain.Lancamento;
import com.prandini.smartwallet.lancamento.repository.LancamentoRepository;
import com.prandini.smartwallet.transacao.service.actions.TransacaoDeleter;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@CommonsLog
public class LancamentoDeleter {

    @Resource
    private LancamentoGetter getter;

    @Resource
    private TransacaoDeleter transacaoDeleter;

    @Resource
    private LancamentoRepository repository;

    @Resource
    private LancamentoValidator validator;;

    public void delete(Long id){

        log.info(String.format("Deletando lancamento por id %s.", id));

        this.validator.validaDelete(id);
//        transacaoDeleter.byLancamento(id);

        this.repository.deleteById(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteByConta(Long id) {
        log.info(String.format("Deletando lancamento por conta %s.", id));
        this.validator.validaDelete(id);
        this.transacaoDeleter.byConta(id);
        this.repository.deleteByConta(id);
    }
}
