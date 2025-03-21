package com.example.registerapp.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import com.example.registerapp.entity.Product;
import com.example.registerapp.repository.ProductRepository;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProductsByCategorySortedByPrice(String categoryName) {
        return productRepository.findByCategory_CategoryNameOrderByPriceAsc(categoryName);
    }

}
