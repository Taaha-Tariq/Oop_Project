package com.oopsproject.models;

import jakarta.persistence.*;
import java.util.List;

// Using the annotation so that this class is mapped to a table in the database
@Entity
@Table(name = "product") // The name of the table
public class Product {
    // This attribute is to be used as the primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // It will have auto generated (auto incremented values)
    private int productId;

    // Many-to-one relationship with ShopOwner
    @ManyToOne
    @JoinColumn(name = "shop_id", referencedColumnName = "shop_id", nullable = false)
    private ShopOwner shopOwner;

    // Defining the attribute productName which can't be null
    @Column(nullable = false)
    private String productName;

    // Defining the attribute description as TEXT
    @Column(columnDefinition = "TEXT")
    private String description;

    // Defining the attribute price which can't be null
    @Column(nullable = false)
    private float price;

    // Defining the attribute stockQuantity
    @Column
    private int stockQuantity;

    // Many-to-one relationship with ProductCategory
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private ProductCategory category;

    // One-to-many relationship with ProductImage
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images;

    // One-to-many relationship with CartItem
    @OneToMany(mappedBy = "product")
    private List<CartItem> cartItems;

    // One-to-many relationship with OrderItem
    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;

    // Default constructor
    public Product() {
    }

    // Parametrized constructor
    public Product(ShopOwner shopOwner, String productName, String description, float price,
                   int stockQuantity, ProductCategory category) {
        this.shopOwner = shopOwner;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
    }

    // Getters for the field attributes
    public int getProductId() {
        return productId;
    }

    public ShopOwner getShopOwner() {
        return shopOwner;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public List<ProductImage> getImages() {
        return images;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    // Setters for the field attributes
    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setShopOwner(ShopOwner shopOwner) {
        this.shopOwner = shopOwner;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public void setImages(List<ProductImage> images) {
        this.images = images;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    // Helper methods
    public void addImage(ProductImage image) {
        images.add(image);
        image.setProduct(this);
    }

    public void removeImage(ProductImage image) {
        images.remove(image);
        image.setProduct(null);
    }

    // Formatting the object information
    @Override
    public String toString() {
        return "Product{" + "productId=" + productId +
                ", shopOwner=" + (shopOwner != null ? shopOwner.getShopId() : null) +
                ", productName=" + productName +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                ", category=" + (category != null ? category.getProductCategoryId() : null) + '}';
    }
}