package com.prandini.smartwallet.common.rest;

/*
 * @author prandini
 * created 8/7/24
 */

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorResponseAPI {

    private String message;
    private int code;
    private String status;
    private LocalDateTime timestamp;
    private String description;
    private List<String> errors;

}
