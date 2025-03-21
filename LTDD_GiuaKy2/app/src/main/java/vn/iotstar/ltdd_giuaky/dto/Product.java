package vn.iotstar.ltdd_giuaky.dto;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("productName")
    private String productName;

    @SerializedName("price")
    private double price;

    @SerializedName("image")
    private String image;

    @SerializedName("createdDate")
    private String createdDate;

    @SerializedName("category")
    private Category category;

    // Getters và Setters
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}