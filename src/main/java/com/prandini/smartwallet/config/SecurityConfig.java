package com.prandini.smartwallet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author kaiooliveira
 * created 22/01/2025
 */

@Configuration
public class SecurityConfig {

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAll
        return new UrlBasedCorsConfigurationSource();
    }

    public SecurityFilterChain
}
