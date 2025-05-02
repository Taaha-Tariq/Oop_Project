
package com.oopsproject.models;

import jakarta.persistence.*;

// Using the annotation so that this class is mapped to a table in the database
@Entity
@Table(name = "cart_items") // The name of the table
public class CartItem {
    // This attribute is to be used as the primary key
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // It will have auto generated (auto incremented values)
    private Long cartItemId;

    // Defining the relationship with Cart entity
    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    // Defining the relationship with Product entity
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Defining the attribute quantity which can't be null
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    // Default constructor
    public CartItem() {
    }

    // Parametrized constructor
    public CartItem(Cart cart, Product product, Integer quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    // Getters for the field attributes
    public Long getCartItemId() {
        return cartItemId;
    }

    public Cart getCart() {
        return cart;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    // Setters for the field attributes
    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    // Formatting the object information
    @Override
    public String toString() {
        return "CartItem{" + "id=" + cartItemId +
                ", cart=" + (cart != null ? cart.getCartId() : null) +
                ", product=" + (product != null ? product.getProductId() : null) +
                ", quantity=" + quantity + '}';
    }
}