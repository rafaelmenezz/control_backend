package com.tcscontrol.control_backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tcscontrol.control_backend.filter.JwtAuthFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {
    @Autowired
    JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/refreshToken").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users/recover-password").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/users/{id}/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/users/{id}/*").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/api/users/*").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/api/users/*").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/api/users/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/api/users/{id}/change-password").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/api/requests").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/api/requests/*").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/api/requests").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/api/requests/devolver-patrimonio").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/api/construction").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/api/construction/*").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/api/fornecedor/*").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/api/allocation/*").hasRole("GESTOR")
                        .requestMatchers(HttpMethod.POST, "/api/allocation/*").hasRole("GESTOR")
                        .requestMatchers(HttpMethod.GET, "/api/maintenance").hasRole("GESTOR")
                        .requestMatchers(HttpMethod.POST, "/api/maintenance").hasRole("GESTOR")
                        .requestMatchers(HttpMethod.PUT, "/api/maintenance/*").hasRole("GESTOR")
                        .requestMatchers(HttpMethod.POST, "/api/fornrcedor/*").hasRole("GESTOR")
                        .requestMatchers(HttpMethod.PATCH, "/api/fornrcedor/*").hasRole("GESTOR")
                        .requestMatchers(HttpMethod.PATCH, "/api/inventory/*").hasRole("GESTOR")
                        .requestMatchers(HttpMethod.POST, "/api/inventory/*").hasRole("GESTOR")
                        .requestMatchers(HttpMethod.GET, "/api/inventory/*").hasRole("GESTOR")
                        .requestMatchers(HttpMethod.GET, "/api/inventory").hasRole("GESTOR")
                        .requestMatchers(HttpMethod.GET, "/api/patrimony/*").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/api/patrimony").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/api/patrimony").hasRole("GESTOR")
                        .requestMatchers(HttpMethod.POST, "/api/patrimony/*").hasRole("GESTOR")
                        .requestMatchers(HttpMethod.PUT, "/api/patrimony/*").hasRole("GESTOR")
                        .requestMatchers(HttpMethod.GET, "/api/requests").hasRole("GESTOR")
                        .requestMatchers(HttpMethod.GET, "/api/requests/*").hasRole("GESTOR")
                        .requestMatchers(HttpMethod.PUT, "/api/requests/retirar").hasRole("GESTOR")
                        .requestMatchers(HttpMethod.PUT, "/api/requests/cancelar").hasRole("GESTOR")
                        .requestMatchers(HttpMethod.PUT, "/api/allocation/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/users").hasRole("GESTOR")
                        .requestMatchers(HttpMethod.POST, "/api/users").hasRole("ADMIN")
                        .anyRequest().permitAll())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return objectMapper;
    }

}
