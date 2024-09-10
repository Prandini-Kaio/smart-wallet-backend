package com.prandini.smartwallet.assinatura.service.actions;

/*
 * @author prandini
 * created 9/4/24
 */

import com.prandini.smartwallet.assinatura.domain.Assinatura;
import com.prandini.smartwallet.assinatura.model.AssinaturaFilter;
import com.prandini.smartwallet.assinatura.repository.AssinaturaRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AssinaturaGetter {

    @Resource
    private AssinaturaRepository repository;

    public List<Assinatura> byFilter(AssinaturaFilter filter){
        return this.repository.byFilter(filter);
    }
}
