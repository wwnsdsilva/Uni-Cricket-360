package com.nsbm.uni_cricket_360.config;

import com.nsbm.uni_cricket_360.filters.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // default strength 10
    }

    // For temporarily disable security for all endpoints
    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // disable CSRF for testing with curl/Postman
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // allow all endpoints
                )
                .formLogin().disable()
                .httpBasic().disable();

        return http.build();
    }*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         http.csrf().disable()
                /*.authorizeHttpRequests()
                .antMatchers("/api/v1/auth/**").permitAll()
                .antMatchers("/api/v1/admin/**").hasRole("ADMIN")
                .antMatchers("/api/v1/coach/**").hasAnyRole("COACH", "ADMIN")
                .antMatchers("/api/v1/player/**").hasAnyRole("PLAYER", "COACH", "ADMIN")
                .anyRequest().authenticated()*/
                 .authorizeHttpRequests(auth -> auth
                         .antMatchers("/api/v1/auth/**").permitAll()
                         .antMatchers("/api/v1/admin/**").hasRole("ADMIN")
                         .antMatchers("/api/v1/coach/**").hasAnyRole("COACH", "ADMIN")
                         .antMatchers("/api/v1/player/**").hasAnyRole("PLAYER", "COACH", "ADMIN")
                         .anyRequest().authenticated()
                 )
                 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                 .and()
                 .formLogin().disable()
                 .httpBasic().disable();

        // Add JWT filter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
