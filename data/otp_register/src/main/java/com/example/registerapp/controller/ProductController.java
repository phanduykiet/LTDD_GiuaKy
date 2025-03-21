package com.example.registerapp.controller;

import com.example.registerapp.entity.Product;
import com.example.registerapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/productsByCategory")
    public ResponseEntity<List<Product>> verifyAccount(@RequestParam String categoryName) {
        List<Product> products = productService.getAllProductsByCategorySortedByPrice(categoryName);
        if (!products.isEmpty()) {
            return ResponseEntity.ok(products);
        }
        return ResponseEntity.notFound().build();
      }

}
