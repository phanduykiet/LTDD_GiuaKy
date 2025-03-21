package vn.iotstar.ltdd_giuaky.dto;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("categoryName")
    private String categoryName;

    @SerializedName("image")
    private String image;

    // Getters và Setters
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}