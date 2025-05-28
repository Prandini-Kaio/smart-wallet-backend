package com.prandini.smartwallet.assinatura.service.actions;

/*
 * @author prandini
 * created 9/4/24
 */

import com.prandini.smartwallet.assinatura.domain.Assinatura;
import com.prandini.smartwallet.assinatura.model.AssinaturaFilter;
import com.prandini.smartwallet.assinatura.repository.AssinaturaRepository;
import com.prandini.smartwallet.common.exception.CommonExceptionSupplier;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AssinaturaGetter {

    @Resource
    private AssinaturaRepository repository;

    public Assinatura byId(Long id){
        return this.repository.findById(id).orElseThrow(CommonExceptionSupplier.naoEncontrado("Assinatura", id.toString()));
    }

    public List<Assinatura> byFilter(AssinaturaFilter filter){
        return this.repository.byFilter(filter);
    }
}
