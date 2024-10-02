package com.bitsnbytes.product.service;

import com.bitsnbytes.product.dto.ProductDTO;
import com.bitsnbytes.product.entity.Category;
import com.bitsnbytes.product.entity.Product;
import com.bitsnbytes.product.exception.CategoryNotFoundException;
import com.bitsnbytes.product.mapper.ProductMapper;
import com.bitsnbytes.product.repository.CategoryRepository;
import com.bitsnbytes.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public ProductDTO createProduct(ProductDTO productDTO){

        Category category= categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(()-> new CategoryNotFoundException("Category id: "
                        + productDTO.getCategoryId() + " not found!"));

        // DTO -> entity
        Product product = ProductMapper.toProductEntity(productDTO, category);
        product = productRepository.save(product);
        // Entity -> DTO
        return ProductMapper.toProductDTO(product);

    }

    // Get All Products
    public List<ProductDTO> getAllProducts(){
        return productRepository.findAll().stream().map(ProductMapper::toProductDTO).toList();
    }

    // Get Product by id
    public ProductDTO getProductById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found!"));
        return ProductMapper.toProductDTO(product);
    }

    // Update Product
    public ProductDTO updateProduct(Long id, ProductDTO productDTO){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found!"));
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found!"));

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setCategory(category);
        productRepository.save(product);
        return ProductMapper.toProductDTO(product);
    }

    public String deleteProduct(Long id){
        productRepository.deleteById(id);
        return "Product "+ id + " has been deleted!";
    }
}
