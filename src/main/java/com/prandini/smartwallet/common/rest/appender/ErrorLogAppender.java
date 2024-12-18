package com.prandini.smartwallet.common.rest.appender;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.ThrowableProxyUtil;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.CoreConstants;
import com.prandini.smartwallet.common.rest.model.ErrorLogInput;
import com.prandini.smartwallet.common.rest.service.ErrorLogService;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.SmartLifecycle;

import java.util.Set;

/*
 * @author prandini
 * created 12/17/24
 */

@CommonsLog
public class ErrorLogAppender extends AppenderBase<ILoggingEvent> implements SmartLifecycle {

    private static ErrorLogService service;

    // Ignorar erros das classes abaixo
    private final Set<String> ignoredClassNames = Set.of(

    );

    @Override
    protected void append(ILoggingEvent iLoggingEvent) {

        if(iLoggingEvent.getLevel().isGreaterOrEqual(Level.ERROR)){
            IThrowableProxy proxy = iLoggingEvent.getThrowableProxy();
            StringBuilder sb = new StringBuilder();

            String message = null;

            if(proxy != null){
                if(shouldIgnoreException(proxy)){
                    log.warn(String.format("Ignorando exceção da classe %s e continuando execução", proxy.getClassName()));
                    return;
                }

                String thrMessage = ThrowableProxyUtil.asString(proxy);
                sb.append(thrMessage);
                sb.append(CoreConstants.LINE_SEPARATOR);
                message = thrMessage;
            }

            message = message != null ? message : iLoggingEvent.getMessage();

            ErrorLogInput errorLog = ErrorLogInput.builder()
                    .errorMessage(StringUtils.truncate(message, 255))
                    .stackTrace(sb.toString())
                    .timestamp(iLoggingEvent.getTimeStamp())
                    .build();

            log.info(String.format("Registro de erro nivel [%s] com mensagem [%s]", iLoggingEvent.getLevel(), message));

            service.create(errorLog);
        }
    }

    private boolean shouldIgnoreException(IThrowableProxy throwableProxy) {
        return ignoredClassNames.contains(throwableProxy.getClassName());
    }

    @Override
    public boolean isRunning() {
        return isStarted();
    }
}
