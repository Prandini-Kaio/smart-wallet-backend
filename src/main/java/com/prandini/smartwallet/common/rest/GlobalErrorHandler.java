package com.prandini.smartwallet.common.rest;

/*
 * @author prandini
 * created 8/7/24
 */

import com.prandini.smartwallet.common.exception.BusinessException;
import com.prandini.smartwallet.common.rest.domain.ErrorLog;
import com.prandini.smartwallet.common.rest.model.ErrorLogInput;
import com.prandini.smartwallet.common.rest.model.ErrorResponseOutput;
import com.prandini.smartwallet.common.rest.service.actions.ErrorLogCreator;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@CommonsLog
@RestControllerAdvice
public class GlobalErrorHandler {

    final String CAMPOS_INVALIDOS_MSG = "Campos inválidos.";

    @Resource
    private ErrorLogCreator creator;

    @ExceptionHandler({
            NoSuchElementException.class,
            EntityNotFoundException.class,
            EmptyResultDataAccessException.class
    })
    public ResponseEntity<ErrorResponseOutput> handleOptionalNotFoundException(Exception ex, WebRequest request) {
        return this.handleError(HttpStatus.NOT_FOUND, ex, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseOutput> handleRuntimeException(DataIntegrityViolationException ex, WebRequest request) {
        return this.handleError(HttpStatus.INTERNAL_SERVER_ERROR, ex, request);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseOutput> handleRuntimeException(RuntimeException ex, WebRequest request) {
        return this.handleError(HttpStatus.INTERNAL_SERVER_ERROR, ex, request);
    }

    @ExceptionHandler({
            BusinessException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<ErrorResponseOutput> handleBusinessException(Exception ex, WebRequest request) {
        return this.handleError(HttpStatus.BAD_REQUEST, ex, request);
    }

    @ExceptionHandler({
            ValidationException.class
    })
    public ResponseEntity<ErrorResponseOutput> handleValidationException(Exception ex, WebRequest request) {

        createLogError(ex);

        return this.handleError(ex.getCause().getMessage(), request);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponseOutput> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        return this.getMethodArgumentoNotValidResponse(HttpStatus.BAD_REQUEST, ex, request);
    }

    private ResponseEntity<ErrorResponseOutput> handleError(String exMessage, WebRequest request) {
        log.error(exMessage);
        List<String> errors = new ArrayList<>();
        errors.add(exMessage);
        return this.getErrorResponse(request, errors);
    }

    private ResponseEntity<ErrorResponseOutput> getErrorResponse(WebRequest request, List<String> errors) {
        ErrorResponseOutput body = new ErrorResponseOutput(
                CAMPOS_INVALIDOS_MSG,
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                LocalDateTime.now(),
                request.getDescription(false),
                errors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    private ResponseEntity<ErrorResponseOutput> getErrorResponse(HttpStatus status, String exMessage, WebRequest request) {
        ErrorResponseOutput body = new ErrorResponseOutput(
                exMessage,
                status.value(),
                status.getReasonPhrase(),
                LocalDateTime.now(),
                request.getDescription(false),
                new ArrayList<>()
        );
        return ResponseEntity.status(status).body(body);
    }

    private ResponseEntity<ErrorResponseOutput> getMethodArgumentoNotValidResponse(HttpStatus status, MethodArgumentNotValidException ex, WebRequest request) {
        final List<String> erros = getMethodArgumentNotValidMessage(status, ex, request);
        ErrorResponseOutput body = new ErrorResponseOutput(
                CAMPOS_INVALIDOS_MSG,
                status.value(),
                status.getReasonPhrase(),
                LocalDateTime.now(),
                request.getDescription(false),
                erros
        );
        return ResponseEntity.status(status).body(body);
    }

    private List<String> getMethodArgumentNotValidMessage(HttpStatus status, MethodArgumentNotValidException e, WebRequest request) {
        if (StringUtils.isEmpty(e.getBindingResult().getFieldErrors ().toString())) {
            return new ArrayList<>();
        }
        return e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(GlobalErrorHandler::formatError)
                .collect(Collectors.toList());
    }

    private ResponseEntity<ErrorResponseOutput> handleError(HttpStatus status, Throwable ex, WebRequest request) {

        log.error(ex.getMessage());

        createLogError(ex);

        return this.getErrorResponse(status, ex.getMessage(), request);
    }

    private static String formatError(FieldError error) {
        StringBuilder builder = new StringBuilder();
        if(!StringUtils.isEmpty(error.getDefaultMessage())){
            builder.append(error.getDefaultMessage());
        }
        if(!StringUtils.isEmpty(error.getField())){
            builder.append(" - ").append(error.getField());
        }
        if(error.getRejectedValue() != null && !StringUtils.isEmpty(error.getRejectedValue().toString())){
            builder.append(" - ").append(error.getRejectedValue());
        }
        return builder.toString();
    }

    private String getStackTraceAsString(Throwable ex) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : ex.getStackTrace()) {
            sb.append(element.toString()).append("\n");
        }
        return sb.toString();
    }

    private void createLogError(Throwable ex) {
        this.creator.create(ErrorLogInput.builder()
                .errorMessage(ex.getMessage())
                .stackTrace(getStackTraceAsString(ex))
                .operador("DEFAULT")
                .timestamp(LocalDateTime.now())
                .build());
    }
}
