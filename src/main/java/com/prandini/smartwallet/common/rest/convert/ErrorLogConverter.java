package com.prandini.smartwallet.common.rest.convert;

/*
 * @author prandini
 * created 12/17/24
 */

import com.prandini.smartwallet.common.rest.domain.ErrorLog;
import com.prandini.smartwallet.common.rest.model.ErrorLogOutput;
import org.springframework.stereotype.Component;

@Component
public class ErrorLogConverter {

    public ErrorLogOutput toOutput(ErrorLog errorLog){
        return ErrorLogOutput.builder()
                .id(errorLog.getId())
                .errorMessage(errorLog.getErrorMessage())
                .stacktrace(errorLog.getStackTrace())
                .operador(errorLog.getOperador())
                .timestamp(errorLog.getTimestamp())
                .build();
    }
}
