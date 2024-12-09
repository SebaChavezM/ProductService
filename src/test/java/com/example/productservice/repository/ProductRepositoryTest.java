package com.example.productservice.repository;

import com.example.productservice.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ProductRepositoryTest {

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        // Arrange
        Long expectedId = 1L;
        String expectedName = "Test Product";
        String expectedDescription = "Test Description";
        Double expectedPrice = 100.0;
        Integer expectedStock = 10;
        String expectedImageUrl = "http://example.com/image.jpg";

        Product product = new Product(expectedName, expectedDescription, expectedPrice, expectedStock, expectedImageUrl);
        product.setId(expectedId);

        when(productRepository.findById(expectedId)).thenReturn(Optional.of(product));

        // Act
        Optional<Product> result = productRepository.findById(expectedId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedId, result.get().getId());
        assertEquals(expectedName, result.get().getName());
        assertEquals(expectedDescription, result.get().getDescription());
        assertEquals(expectedPrice, result.get().getPrice());
        assertEquals(expectedStock, result.get().getStock());
        assertEquals(expectedImageUrl, result.get().getImageUrl());
    }
}
