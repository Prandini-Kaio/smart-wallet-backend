package com.prandini.smartwallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.prandini.smartwallet"})
@EnableJpaRepositories(basePackages = "com.prandini.smartwallet", repositoryImplementationPostfix = "CustomImpl")
public class SmartWalletBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartWalletBackendApplication.class, args);
    }

}
