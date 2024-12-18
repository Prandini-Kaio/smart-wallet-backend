package com.prandini.smartwallet.common.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/*
 * @author prandini
 * created 12/17/24
 */

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class ErrorLogOutput {

    private Long id;

    private String errorMessage;

    private String stacktrace;

    private String operador;

    private LocalDateTime timestamp;

}
