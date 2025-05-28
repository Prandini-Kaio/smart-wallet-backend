package com.prandini.smartwallet.common.rest.controller;

/*
 * @author prandini
 * created 12/17/24
 */

import com.prandini.smartwallet.common.rest.model.ErrorLogOutput;
import com.prandini.smartwallet.common.rest.service.ErrorLogService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/errors")
public class ErrorLogController {

    @Resource
    private ErrorLogService service;

    @GetMapping
    @Operation(description = "")
    public ResponseEntity<List<ErrorLogOutput>> getErrorLog() {
        return ResponseEntity.ok().body(service.findAll());
    }
}
