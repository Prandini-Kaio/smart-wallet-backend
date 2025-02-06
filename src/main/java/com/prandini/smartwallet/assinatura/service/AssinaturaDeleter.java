package com.prandini.smartwallet.assinatura.service;

import com.prandini.smartwallet.assinatura.repository.AssinaturaRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @author kaiooliveira
 * created 06/02/2025
 */

@Component
public class AssinaturaDeleter {

    @Resource
    private AssinaturaRepository repository;

    public void delete(Long id){
        this.repository.deleteById(id);
    }
}
