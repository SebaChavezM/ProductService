package com.example.productservice.controller;

import com.example.productservice.entity.Product;
import com.example.productservice.entity.CartItem;
import com.example.productservice.service.ProductService;
import com.example.productservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        // Arrange
        List<Product> mockProducts = List.of(
                new Product("Product A", "Description A", 100.0, 10, "imageA"),
                new Product("Product B", "Description B", 200.0, 20, "imageB")
        );
        when(productRepository.findAll()).thenReturn(mockProducts);

        // Act
        ResponseEntity<?> response = productController.getAllProducts();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProducts, response.getBody());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testCreateProduct() {
        // Arrange
        Product product = new Product("Product A", "Description A", 100.0, 10, "imageA");
        when(productRepository.save(product)).thenReturn(product);

        // Act
        ResponseEntity<?> response = productController.createProduct(product);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testUpdateProduct_Success() {
        // Arrange
        Long productId = 1L;
        Product existingProduct = new Product("Old Name", "Old Description", 50.0, 5, "oldImage");
        Product updatedDetails = new Product("New Name", null, 75.0, null, null);
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);

        // Act
        ResponseEntity<Product> response = productController.updateProduct(productId, updatedDetails);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("New Name", response.getBody().getName());
        assertEquals(75.0, response.getBody().getPrice());
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    void testUpdateProduct_NotFound() {
        // Arrange
        Long productId = 1L;
        Product updatedDetails = new Product("New Name", null, 75.0, null, null);
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Product> response = productController.updateProduct(productId, updatedDetails);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testDeleteProduct_Success() {
        // Arrange
        Long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = productController.deleteProduct(productId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void testDeleteProduct_NotFound() {
        // Arrange
        Long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(false);

        // Act
        ResponseEntity<Void> response = productController.deleteProduct(productId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(productRepository, times(0)).deleteById(productId);
    }

    @Test
    void testReduceStock_Success() {
        // Arrange
        Long productId = 1L;
        Product product = new Product("Product A", "Description A", 100.0, 10, "imageA");
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        ResponseEntity<?> response = productController.reduceStock(productId, 5);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Stock updated successfully", response.getBody());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testReduceStock_InsufficientStock() {
        // Arrange
        Long productId = 1L;
        Product product = new Product("Product A", "Description A", 100.0, 2, "imageA");
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        ResponseEntity<?> response = productController.reduceStock(productId, 5);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Insufficient stock", response.getBody());
    }

    @Test
    void testCheckout_Success() {
        // Arrange
        List<CartItem> cartItems = List.of(
                new CartItem(1L, "Product A", 2),
                new CartItem(2L, "Product B", 1)
        );
        when(productService.reduceStock(1L, 2)).thenReturn(true);
        when(productService.reduceStock(2L, 1)).thenReturn(true);

        // Act
        ResponseEntity<Map<String, String>> response = productController.checkout(cartItems);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Compra realizada con éxito", response.getBody().get("message"));
    }

    @Test
    void testCheckout_InsufficientStock() {
        // Arrange
        List<CartItem> cartItems = List.of(
                new CartItem(1L, "Product A", 5)
        );
        when(productService.reduceStock(1L, 5)).thenReturn(false);

        // Act
        ResponseEntity<Map<String, String>> response = productController.checkout(cartItems);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No hay suficiente stock para el producto: Product A", response.getBody().get("message"));
    }
}
