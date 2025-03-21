package com.example.registerapp.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.registerapp.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String>{
	
	List<Category> findAll();
}


