package com.example.registerapp.entity;

import java.sql.Timestamp;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Product {
    
    @Id
    private String productName;
    private double price;
    private String image;
    private Timestamp createdDate;
    @ManyToOne
    @JoinColumn(name = "categoryName", nullable = false)
    private Category category;
}
