package com.nsbm.uni_cricket_360.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // default strength 10
    }

    // For temporily disable security for all endpoints
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // disable CSRF for testing with curl/Postman
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // allow all endpoints
                )
                .formLogin().disable()
                .httpBasic().disable();

        return http.build();
    }
}
