package com.example.productservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Habilita Cross-Origin Resource Sharing (CORS)
            .cors(cors -> cors.disable()) // Si necesitas configurar CORS en detalle, crea un filtro separado.
            // Desactiva la protección CSRF si no estás utilizando formularios o necesitas permitir llamadas API directas.
            .csrf(csrf -> csrf.disable())
            // Configura las reglas de autorización
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/products/**").permitAll()
                .requestMatchers("/api/restricted").authenticated()
                .anyRequest().authenticated() // Requiere autenticación para cualquier otra solicitud
            )
            // Usa autenticación básica (opcional, útil para pruebas)
            .httpBasic();
        
        return http.build();
    }
}
