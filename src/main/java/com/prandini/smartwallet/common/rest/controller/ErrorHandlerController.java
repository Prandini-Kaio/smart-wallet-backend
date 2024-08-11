package com.prandini.smartwallet.common.rest.controller;

/*
 * @author prandini
 * created 8/7/24
 */

import com.prandini.smartwallet.common.exception.BusinessException;
import com.prandini.smartwallet.common.rest.ErrorResponseAPI;
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
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@CommonsLog
@RestControllerAdvice
public class ErrorHandlerController {
    final String CAMPOS_INVALIDOS_MSG = "Campos inválidos.";

    @ExceptionHandler({
            NoSuchElementException.class,
            EntityNotFoundException.class,
            EmptyResultDataAccessException.class
    })
    public ResponseEntity<ErrorResponseAPI> handleOptionalNotFoundException(Exception ex, WebRequest request) {
        return this.handleError(HttpStatus.NOT_FOUND, ex, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseAPI> handleRuntimeException(DataIntegrityViolationException ex, WebRequest request) {
        return this.handleError(HttpStatus.INTERNAL_SERVER_ERROR, ex, request);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseAPI> handleRuntimeException(RuntimeException ex, WebRequest request) {
        return this.handleError(HttpStatus.INTERNAL_SERVER_ERROR, ex, request);
    }

    @ExceptionHandler({
            BusinessException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<ErrorResponseAPI> handleBusinessException(Exception ex, WebRequest request) {
        return this.handleError(HttpStatus.BAD_REQUEST, ex, request);
    }

    @ExceptionHandler({
            ValidationException.class
    })
    public ResponseEntity<ErrorResponseAPI> handleValidationException(Exception ex, WebRequest request) {
        return this.handleError(ex.getCause().getMessage(), request);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponseAPI> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        return this.getMethodArgumentoNotValidResponse(HttpStatus.BAD_REQUEST, ex, request);
    }

    private ResponseEntity<ErrorResponseAPI> handleError(String exMessage, WebRequest request) {
        log.error(exMessage);
        List<String> errors = new ArrayList<>();
        errors.add(exMessage);
        return this.getErrorResponse(request, errors);
    }

    private ResponseEntity<ErrorResponseAPI> getErrorResponse(WebRequest request, List<String> errors) {
        ErrorResponseAPI body = new ErrorResponseAPI(
                CAMPOS_INVALIDOS_MSG,
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                LocalDateTime.now(),
                request.getDescription(false),
                errors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    private ResponseEntity<ErrorResponseAPI> getErrorResponse(HttpStatus status, String exMessage, WebRequest request) {
        ErrorResponseAPI body = new ErrorResponseAPI(
                exMessage,
                status.value(),
                status.getReasonPhrase(),
                LocalDateTime.now(),
                request.getDescription(false),
                new ArrayList<>()
        );
        return ResponseEntity.status(status).body(body);
    }

    private ResponseEntity<ErrorResponseAPI> getMethodArgumentoNotValidResponse(HttpStatus status, MethodArgumentNotValidException ex, WebRequest request) {
        final List<String> erros = getMethodArgumentNotValidMessage(status, ex, request);
        ErrorResponseAPI body = new ErrorResponseAPI(
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
                .map(ErrorHandlerController::formatError)
                .collect(Collectors.toList());
    }

    private ResponseEntity<ErrorResponseAPI> handleError(HttpStatus status, Throwable ex, WebRequest request) {
        return this.handleError(status, ex.getMessage(), request);
    }

    private ResponseEntity<ErrorResponseAPI> handleError(HttpStatus status, String exMessage, WebRequest request) {
        log.error(exMessage);
        return this.getErrorResponse(status, exMessage, request);
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
}
