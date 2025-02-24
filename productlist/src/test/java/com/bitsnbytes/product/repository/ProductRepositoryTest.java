package com.bitsnbytes.product.repository;

import com.bitsnbytes.product.entity.Category;
import com.bitsnbytes.product.entity.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Product product;
    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setName("Electronics");
        categoryRepository.save(category);

        product = new Product();
        product.setName("Laptop");
        product.setDescription("A high-end gaming laptop");
        product.setPrice(1500.00);
        product.setCategory(category);
        productRepository.save(product);
    }

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void testFindById() {
        Optional<Product> foundProduct = productRepository.findById(product.getId());
        assertTrue(foundProduct.isPresent());
        assertEquals(product.getName(), foundProduct.get().getName());
    }

    @Test
    void testFindAll() {
        List<Product> products = productRepository.findAll();
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
    }

    @Test
    void testSave() {
        Product newProduct = new Product();
        newProduct.setName("Smartphone");
        newProduct.setDescription("A latest model smartphone");
        newProduct.setPrice(800.00);
        newProduct.setCategory(category);
        Product savedProduct = productRepository.save(newProduct);
        assertNotNull(savedProduct);
        assertEquals(newProduct.getName(), savedProduct.getName());
    }

    @Test
    void testDelete() {
        productRepository.delete(product);
        Optional<Product> foundProduct = productRepository.findById(product.getId());
        assertFalse(foundProduct.isPresent());
    }
}