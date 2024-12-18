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

    private String errorMessage;

    private String stackTrace;

    private LocalDateTime timestamp;
}
