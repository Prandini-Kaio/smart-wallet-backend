package com.prandini.smartwallet.health;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @author prandini
 * created 5/6/24
 */

@RestController
@RequestMapping("/health")
public class HealthController {


    @GetMapping("/ping")
    public ResponseEntity<String> ping(){
        return ResponseEntity.ok("pong");
    }
}
