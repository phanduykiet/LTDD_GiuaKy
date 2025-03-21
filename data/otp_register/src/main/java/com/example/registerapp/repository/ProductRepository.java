package com.example.registerapp.repository;

import com.example.registerapp.entity.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
	
	List<Product> findByCategory_CategoryNameOrderByPriceAsc(String categoryName);
}
