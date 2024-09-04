package com.prandini.smartwallet.assinatura.service;

import com.prandini.smartwallet.assinatura.model.AssinaturaInput;
import com.prandini.smartwallet.assinatura.model.AssinaturaOutput;
import com.prandini.smartwallet.assinatura.service.actions.AssinaturaCreator;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author kaiooliveira
 * created 04/09/2024
 */

@Service
public class AssinaturaService {

    @Resource
    private AssinaturaCreator creator;

    public AssinaturaOutput criarAssinatura(AssinaturaInput input) {
        return null;
    }
}
