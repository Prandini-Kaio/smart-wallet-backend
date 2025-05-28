package com.prandini.smartwallet.common.rest.model;

/*
 * @author prandini
 * created 12/17/24
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor @AllArgsConstructor
public class ErrorLogInput {

    private Long id;

    private String errorMessage;

    private String stackTrace;

    private String operador;

    private LocalDateTime timestamp;
}
