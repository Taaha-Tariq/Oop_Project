package com.oopsproject.models;

import jakarta.persistence.*;

// Using the annotation so that this class is mapped to a table in the database
@Entity
@Table(name = "order_items") // The name of the table
public class OrderItem {
    // This attribute is to be used as the primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // It will have auto generated (auto incremented values)
    private Long orderItemId;

    // Defining the relationship with Order entity
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // Defining the relationship with Product entity
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Defining the attribute quantity which can't be null
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    // Defining the attribute price which can't be null
    @Column(name = "price", nullable = false)
    private Float price;

    // Default constructor
    public OrderItem() {
    }

    // Parametrized constructor
    public OrderItem(Order order, Product product, Integer quantity, Float price) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters for the field attributes
    public Long getOrderItemId() {
        return orderItemId;
    }

    public Order getOrder() {
        return order;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Float getPrice() {
        return price;
    }

    // Setters for the field attributes
    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    // Formatting the object information
    @Override
    public String toString() {
        return "OrderItem{" + "id=" + orderItemId +
                ", order=" + (order != null ? order.getId() : null) +
                ", product=" + (product != null ? product.getProductId() : null) +
                ", quantity=" + quantity +
                ", price=" + price + '}';
    }
}