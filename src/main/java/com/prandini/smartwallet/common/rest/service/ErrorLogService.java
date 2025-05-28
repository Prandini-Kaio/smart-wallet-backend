package com.prandini.smartwallet.common.rest.service;

/*
 * @author prandini
 * created 12/17/24
 */

import com.prandini.smartwallet.common.rest.convert.ErrorLogConverter;
import com.prandini.smartwallet.common.rest.domain.ErrorLog;
import com.prandini.smartwallet.common.rest.model.ErrorLogInput;
import com.prandini.smartwallet.common.rest.model.ErrorLogOutput;
import com.prandini.smartwallet.common.rest.model.ErrorResponseOutput;
import com.prandini.smartwallet.common.rest.service.actions.ErrorLogCreator;
import com.prandini.smartwallet.common.rest.service.actions.ErrorLogGetter;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@CommonsLog
public class ErrorLogService {

    @Resource
    private ErrorLogGetter getter;

    @Resource
    private ErrorLogCreator creator;

    @Resource
    private ErrorLogConverter converter;

    public List<ErrorLogOutput> findAll(){
        log.info("Iniciando busca de todos os logs de erro do sistema.");
        return getter.findAll().stream().sorted(Comparator.comparing(ErrorLog::getTimestamp).reversed()).map(converter::toOutput).toList();
    }

    public ErrorLogOutput create(ErrorLogInput input) {
        log.info("Iniciando criação de log de erro.");

        return this.converter.toOutput(this.creator.create(input));
    }
}
