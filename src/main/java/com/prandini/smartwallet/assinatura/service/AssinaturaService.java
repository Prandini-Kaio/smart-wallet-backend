package com.prandini.smartwallet.assinatura.service;

import com.prandini.smartwallet.assinatura.converter.AssinaturaConverter;
import com.prandini.smartwallet.assinatura.model.AssinaturaFilter;
import com.prandini.smartwallet.assinatura.model.AssinaturaInput;
import com.prandini.smartwallet.assinatura.model.AssinaturaOutput;
import com.prandini.smartwallet.assinatura.service.actions.AssinaturaCreator;
import com.prandini.smartwallet.assinatura.service.actions.AssinaturaGetter;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kaiooliveira
 * created 04/09/2024
 */

@Service
@CommonsLog
public class AssinaturaService {

    @Resource
    private AssinaturaCreator creator;

    @Resource
    private AssinaturaGetter getter;

    @Resource
    private AssinaturaConverter converter;

    public AssinaturaOutput criarAssinatura(AssinaturaInput input) {
        log.info(String.format("Iniciando criação de assinatura para %s", input.getDescricao()));

        return converter.toOutput(creator.create(input));
    }

    public List<AssinaturaOutput> byFilter(AssinaturaFilter filter) {
        return this.getter.byFilter(filter).stream().map(converter::toOutput).toList();
    }
}
