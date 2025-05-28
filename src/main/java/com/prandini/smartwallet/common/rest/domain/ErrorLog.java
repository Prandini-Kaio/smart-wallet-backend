package com.prandini.smartwallet.common.rest.domain;

/*
 * @author prandini
 * created 12/17/24
 */

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ERROR_LOG")
@Builder
@Data
@NoArgsConstructor @AllArgsConstructor
public class ErrorLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "OPERADOR", nullable = false)
    private String operador;

    @Column(name = "ERROR_MESSAGE", nullable = false)
    private String errorMessage;

    @Column(name = "STACKTRACE")
    private String stackTrace;

    @Column(name = "URI")
    private String uri;

    @Column(name = "TIMESTAMP", nullable = false)
    private LocalDateTime timestamp;
}
