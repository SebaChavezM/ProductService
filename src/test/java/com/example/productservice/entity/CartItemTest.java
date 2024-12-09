package com.example.productservice.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CartItemTest {

    @Test
    void testCartItemConstructorAndGetters() {
        // Arrange
        Long expectedId = 1L;
        String expectedName = "Test Item";
        int expectedQuantity = 5;

        // Act
        CartItem cartItem = new CartItem(expectedId, expectedName, expectedQuantity);

        // Assert
        assertEquals(expectedId, cartItem.getId());
        assertEquals(expectedName, cartItem.getName());
        assertEquals(expectedQuantity, cartItem.getQuantity());
    }

    @Test
    void testCartItemSetters() {
        // Arrange
        CartItem cartItem = new CartItem(null, null, 0);
        Long expectedId = 2L;
        String expectedName = "Updated Item";
        int expectedQuantity = 10;

        // Act
        cartItem.setId(expectedId);
        cartItem.setName(expectedName);
        cartItem.setQuantity(expectedQuantity);

        // Assert
        assertEquals(expectedId, cartItem.getId());
        assertEquals(expectedName, cartItem.getName());
        assertEquals(expectedQuantity, cartItem.getQuantity());
    }
}
