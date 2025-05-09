package com.oopsproject.dto;

import java.util.List;

public class ProductDTO {
    private int productId;
    private ShopOwnerDTO shopOwner; // Assuming you have a ShopOwnerDTO class
    private String productName;
    private String description;
    private float price;
    private int stockQuantity;
    private ProductCategoryDTO category; // Assuming you have a ProductCategoryDTO class
    private List<ProductImageDTO> images; // Assuming you have a ProductImageDTO class

    // getters and setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public ShopOwnerDTO getShopOwner() {
        return shopOwner;
    }

    public void setShopOwner(ShopOwnerDTO shopOwner) {
        this.shopOwner = shopOwner;
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

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
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
}
