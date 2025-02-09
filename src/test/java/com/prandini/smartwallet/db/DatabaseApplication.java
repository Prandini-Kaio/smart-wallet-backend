package com.prandini.smartwallet.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author kaiooliveira
 * created 08/02/2025
 */

@SpringBootApplication(scanBasePackages = {"com.prandini.smartwallet"})
public class DatabaseApplication {

    public static void main(String[] args){
        SpringApplication.run(DatabaseApplication.class,args);
    }
}
