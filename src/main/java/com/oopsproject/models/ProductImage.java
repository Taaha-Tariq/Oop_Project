package com.oopsproject.models;

import jakarta.persistence.*;

@Entity
@Table(name = "PRODUCT_IMAGES")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private int imageId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    // Default constructor
    public ProductImage() {
    }

    // Parameterized constructor
    public ProductImage(Product product, String imageUrl) {
        this.product = product;
        this.imageUrl = imageUrl;
    }

    // Getters for the field attributes
    public int getImageId() {
        return imageId;
    }

    public Product getProduct() {
        return product;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    // Setters for the field attributes
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // Formatting the object information
    @Override
    public String toString() {
        return "ProductImage{" + "imageId=" + imageId + ", product=" + product.getProductId() +
                ", imageUrl='" + imageUrl + '\'' + '}';
    }
}