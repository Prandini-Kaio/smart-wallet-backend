package com.prandini.smartwallet.common.rest.convert;

/*
 * @author prandini
 * created 12/17/24
 */

import com.prandini.smartwallet.common.rest.domain.ErrorLog;
import com.prandini.smartwallet.common.rest.model.ErrorLogOutput;
import com.prandini.smartwallet.common.utils.DateUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

@Component
public class ErrorLogConverter {

    private final View error;

    public ErrorLogConverter(View error) {
        this.error = error;
    }

    public ErrorLogOutput toOutput(ErrorLog errorLog){
        return ErrorLogOutput.builder()
                .id(errorLog.getId())
                .errorMessage(errorLog.getErrorMessage())
                .stacktrace(errorLog.getStackTrace())
                .operador(errorLog.getOperador())
                .timestamp(DateUtils.toBrazilianDateTimeString(errorLog.getTimestamp()))
                .build();
    }
}
