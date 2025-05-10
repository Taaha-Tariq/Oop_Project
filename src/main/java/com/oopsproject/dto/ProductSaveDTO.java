package com.oopsproject.dto;

import java.util.List;

public class ProductSaveDTO {
    private int productId;
    private String productName;
    private String description;
    private float price;
    private int stock;
    private ProductCategoryDTO category; // Assuming you have a ProductCategoryDTO class
    private List<ProductImageDTO> images; // Assuming you have a ProductImageDTO class
    // Only include shopOwnerId, not the entire object
    private Long shopOwnerId;
    private String shopName;
    
    // Getters and setters
    public int getProductId() {
        return productId;
    }
    
    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public float getPrice() {
        return price;
    }
    
    public void setPrice(float price) {
        this.price = price;
    }
    
    public int getStock() {
        return stock;
    }
    
    public void setStock(int stock) {
        this.stock = stock;
    }
    
    public ProductCategoryDTO getCategory() {
        return category;
    }

    public void setCategory(ProductCategoryDTO category) {
        this.category = category;
    }

    public List<ProductImageDTO> getImages() {
        return images;
    }

    public void setImages(List<ProductImageDTO> images) {
        this.images = images;
    }
    
    public Long getShopOwnerId() {
        return shopOwnerId;
    }
    
    public void setShopOwnerId(Long shopOwnerId) {
        this.shopOwnerId = shopOwnerId;
    }
    
    public String getShopName() {
        return shopName;
    }
    
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}