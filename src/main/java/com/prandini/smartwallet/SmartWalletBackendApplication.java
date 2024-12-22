package com.prandini.smartwallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.prandini.smartwallet"})
@EnableJpaRepositories(basePackages = "com.prandini.smartwallet", repositoryImplementationPostfix = "CustomImpl")
@EnableScheduling
@Profile("db-local")
public class SmartWalletBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartWalletBackendApplication.class, args);
    }

}
