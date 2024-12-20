package com.prandini.smartwallet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author kaiooliveira
 * created 20/12/2024
 */

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:8080")
                        .allowedOrigins("http://192.168.1.16:8080")
                        .allowedOrigins("http://0.0.0.0:8080")
                        .allowedOrigins("http://popcorn.internal:8080")
                        .allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }
}
