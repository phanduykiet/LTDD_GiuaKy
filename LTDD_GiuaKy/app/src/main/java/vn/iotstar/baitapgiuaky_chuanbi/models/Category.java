package vn.iotstar.baitapgiuaky_chuanbi.models;

import java.io.Serializable;

public class Category implements Serializable {
    private int categoryId;
    private String categoryName;
    private String image;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

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
