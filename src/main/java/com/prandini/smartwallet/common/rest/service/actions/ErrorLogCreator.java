package com.prandini.smartwallet.common.rest.service.actions;

/*
 * @author prandini
 * created 12/17/24
 */

import com.prandini.smartwallet.common.rest.domain.ErrorLog;
import com.prandini.smartwallet.common.rest.model.ErrorLogInput;
import com.prandini.smartwallet.common.rest.repository.ErrorLogRepository;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;

@Component
@CommonsLog
public class ErrorLogCreator {

    @Resource
    private ErrorLogRepository repository;

    public ErrorLog create(ErrorLogInput input){
        log.info("Criando log de erro.");

        // Converte o timestamp em localdatetime
        LocalDateTime timestamp = LocalDateTime.ofInstant(Instant.ofEpochMilli(input.getTimestamp()), Clock.systemDefaultZone().getZone());

        ErrorLog errorLog = ErrorLog.builder()
                .errorMessage(input.getErrorMessage())
                .timestamp(timestamp)
                .stackTrace(input.getStackTrace())
                .build();

        return this.repository.save(errorLog);
    }
}
