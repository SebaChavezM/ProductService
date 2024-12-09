package com.example.productservice.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {

    @Test
    void testProductConstructorAndGetters() {
        // Arrange
        Long expectedId = 1L;
        String expectedName = "Test Product";
        String expectedDescription = "Test Description";
        Double expectedPrice = 10.0;
        Integer expectedStock = 100;
        String expectedImageUrl = "http://example.com/image.jpg";

        // Act
        Product product = new Product(expectedName, expectedDescription, expectedPrice, expectedStock, expectedImageUrl);
        product.setId(expectedId);

        // Assert
        assertEquals(expectedId, product.getId());
        assertEquals(expectedName, product.getName());
        assertEquals(expectedDescription, product.getDescription());
        assertEquals(expectedPrice, product.getPrice());
        assertEquals(expectedStock, product.getStock());
        assertEquals(expectedImageUrl, product.getImageUrl());
    }

    @Test
    void testProductSetters() {
        // Arrange
        Product product = new Product(null, null, null, null, null);
        Long expectedId = 2L;
        String expectedName = "Updated Product";
        String expectedDescription = "Updated Description";
        Double expectedPrice = 20.0;
        Integer expectedStock = 50;
        String expectedImageUrl = "http://example.com/updated_image.jpg";

        // Act
        product.setId(expectedId);
        product.setName(expectedName);
        product.setDescription(expectedDescription);
        product.setPrice(expectedPrice);
        product.setStock(expectedStock);
        product.setImageUrl(expectedImageUrl);

        // Assert
        assertEquals(expectedId, product.getId());
        assertEquals(expectedName, product.getName());
        assertEquals(expectedDescription, product.getDescription());
        assertEquals(expectedPrice, product.getPrice());
        assertEquals(expectedStock, product.getStock());
        assertEquals(expectedImageUrl, product.getImageUrl());
    }
}
