package com.example.registerapp.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Category {
    
    @Id
    private String categoryName;
    private String image;
}
