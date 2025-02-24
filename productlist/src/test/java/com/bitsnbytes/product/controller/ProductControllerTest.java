package com.bitsnbytes.product.controller;

import com.bitsnbytes.product.dto.ProductDTO;
import com.bitsnbytes.product.service.ProductService;
import lombok.With;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Laptop");
        productDTO.setDescription("A high-end gaming laptop");
        productDTO.setPrice(1500.00);
        productDTO.setCategoryId(1L);
    }

    @Test
    void testGetAllProducts() {
        when(productService.getAllProducts()).thenReturn(List.of(productDTO));

        List<ProductDTO> products = productController.getAllProduct();

        assertNotNull(products);
        assertEquals(1, products.size());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void testGetProductById() {
        when(productService.getProductById(any(Long.class))).thenReturn(productDTO);

        ProductDTO foundProduct = productController.getProductById(1L);

        assertNotNull(foundProduct);
        assertEquals(productDTO.getName(), foundProduct.getName());
        verify(productService, times(1)).getProductById(any(Long.class));
    }

    @Test
    @WithMockUser(authorities = "ROLE_SELLER")
    void testCreateProduct() {
        when(productService.createProduct(any(ProductDTO.class))).thenReturn(productDTO);

        ResponseEntity<ProductDTO> response = productController.createProduct(productDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(productDTO.getName(), response.getBody().getName());
        verify(productService, times(1)).createProduct(any(ProductDTO.class));
    }

    @Test
    @WithMockUser(authorities = "ROLE_SELLER")
    void testUpdateProduct() {
        when(productService.updateProduct(any(Long.class), any(ProductDTO.class))).thenReturn(productDTO);

        ProductDTO updatedProduct = productController.updateProduct(1L, productDTO);

        assertNotNull(updatedProduct);
        assertEquals(productDTO.getName(), updatedProduct.getName());
        verify(productService, times(1)).updateProduct(any(Long.class), any(ProductDTO.class));
    }

    @Test
    @WithMockUser(authorities = "ROLE_SELLER")
    void testDeleteProduct() {
        when(productService.deleteProduct(any(Long.class))).thenReturn("Product 1 has been deleted!");

        String result = productController.deleteProduct(1L);

        assertEquals("Product 1 has been deleted!", result);
        verify(productService, times(1)).deleteProduct(any(Long.class));
    }
}