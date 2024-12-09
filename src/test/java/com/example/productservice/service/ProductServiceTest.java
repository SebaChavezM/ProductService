package com.example.productservice.service;

import com.example.productservice.entity.Product;
import com.example.productservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProductById() {
        // Arrange
        Long productId = 1L;
        Product mockProduct = new Product(
                "Test Product", 
                "Test Description", 
                100.0, 
                10, 
                "http://example.com/image.jpg"
        );
        mockProduct.setId(productId);
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        // Act
        Optional<Product> result = productService.getProductById(productId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(productId, result.get().getId());
        assertEquals("Test Product", result.get().getName());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testSaveProduct() {
        // Arrange
        Product mockProduct = new Product(
                "New Product", 
                "New Description", 
                200.0, 
                20, 
                "http://example.com/new-image.jpg"
        );
        when(productRepository.save(mockProduct)).thenReturn(mockProduct);

        // Act
        Product savedProduct = productService.saveProduct(mockProduct);

        // Assert
        assertNotNull(savedProduct);
        assertEquals("New Product", savedProduct.getName());
        assertEquals(200.0, savedProduct.getPrice());
        verify(productRepository, times(1)).save(mockProduct);
    }

    @Test
    void testDeleteProduct() {
        // Act
        productService.deleteProduct(1L);

        // Assert
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void testReduceStock_Success() {
        // Arrange
        Long productId = 1L;
        Product mockProduct = new Product(
                "Test Product", 
                "Test Description", 
                100.0, 
                10, 
                "http://example.com/image.jpg"
        );
        mockProduct.setId(productId);
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        // Act
        boolean success = productService.reduceStock(productId, 5);

        // Assert
        assertTrue(success);
        assertEquals(5, mockProduct.getStock());
        verify(productRepository, times(1)).save(mockProduct);
    }

    @Test
    void testReduceStock_InsufficientStock() {
        // Arrange
        Long productId = 1L;
        Product mockProduct = new Product(
                "Test Product", 
                "Test Description", 
                100.0, 
                3, 
                "http://example.com/image.jpg"
        );
        mockProduct.setId(productId);
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        // Act
        boolean success = productService.reduceStock(productId, 5);

        // Assert
        assertFalse(success);
        assertEquals(3, mockProduct.getStock()); // Stock should remain unchanged
        verify(productRepository, times(0)).save(mockProduct);
    }
}
