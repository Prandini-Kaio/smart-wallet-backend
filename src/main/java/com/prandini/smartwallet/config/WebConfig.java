package com.prandini.smartwallet.config;

/*
 * @author prandini
 * created 10/24/24
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Permitir todos os endpoints
                        .allowedOrigins("http://localhost:4200") // Permitir requisições do frontend
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
                        .allowedHeaders("*") // Permitir todos os headers
                        .allowCredentials(true); // Se houver autenticação via cookies ou headers
            }
        };
    }
}
