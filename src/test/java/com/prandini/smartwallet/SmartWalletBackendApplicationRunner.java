package com.prandini.smartwallet;

import org.springframework.boot.SpringApplication;

/**
 * @author kaiooliveira
 * created 10/07/2024
 */
public class SmartWalletBackendApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(SmartWalletBackendApplication.class);
    }

    public static SpringApplication getApp(){
        return new SpringApplication(SmartWalletBackendApplication.class);
    }
}
