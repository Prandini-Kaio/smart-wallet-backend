package com.prandini.smartwallet.common.rest.service.actions;

/*
 * @author prandini
 * created 12/17/24
 */

import com.prandini.smartwallet.common.rest.domain.ErrorLog;
import com.prandini.smartwallet.common.rest.repository.ErrorLogRepository;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@CommonsLog
public class ErrorLogGetter {

    @Resource
    private ErrorLogRepository repository;

    public List<ErrorLog> findAll(){
        log.info("Buscando todos os logs de erro do sistema.");
        return this.repository.findAll();
    }
}
