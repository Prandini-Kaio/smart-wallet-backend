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
import java.util.stream.Stream;

@Component
public class AssinaturaGetter {

    @Resource
    private AssinaturaRepository repository;

    public List<Assinatura> streamByFilter(AssinaturaFilter filter){
        return this.repository.streamByFilter(filter);
    }
}
