package com.example.productservice;

import com.example.productservice.CorsConfig;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CorsConfigTest {

    @Test
    void testCorsConfigurerCreation() {
        // Arrange
        CorsConfig corsConfig = new CorsConfig();

        // Act
        WebMvcConfigurer webMvcConfigurer = corsConfig.corsConfigurer();

        // Assert
        assertNotNull(webMvcConfigurer, "The WebMvcConfigurer bean should not be null");
    }
}
