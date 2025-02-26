package com.gradleproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // If you want to disable CSRF entirely (not recommended for production unless you have another strategy),
                // you can disable it:
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz -> authz
                        // Permit all requests to the /users endpoint (or adjust the matcher as needed)
                        .requestMatchers("/users/**").permitAll()
                        // All other endpoints require authentication
                        .anyRequest().authenticated()
                )
                // Enable basic authentication for endpoints that require it
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}