package com.oopsproject.models;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

// Using the annotation so that this class is mapped to a table in the database
@Entity
@Table(name = "orders") // The name of the table
public class Order {
    // This attribute is to be used as the primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // It will have auto generated (auto incremented values)
    private Long orderId;

    // Defining the relationship with Users entity
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    // Defining the attribute orderDate which can't be null
    @Column(name = "order_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)

    private Date orderDate;

    // Defining the attribute status which is an enumerated type and can't be null
    @Column(name = "status", nullable = false)
    private String status;

    // Defining the relationship with OrderItem entities
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    // Defining the relationship with Payment entity
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;

    // Default constructor
    public Order() {
    }

    // Parametrized constructor
    public Order(Users user, Date orderDate, String status) {
        this.user = user;
        this.orderDate = orderDate;
        this.status = status;
    }

    // Getters for the field attributes
    public Long getId() {
        return orderId;
    }

    public Users getUser() {
        return user;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public String getStatus() {
        return status;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public Payment getPayment() {
        return payment;
    }

    // Setters for the field attributes
    public void setId(Long id) {
        this.orderId = id;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    // Formatting the object information
    @Override
    public String toString() {
        return "Order{" + "id=" + orderId +
                ", user=" + (user != null ? user.getUserId() : null) +
                ", orderDate=" + orderDate +
                ", status=" + status +
                ", orderItems=" + (orderItems != null ? orderItems.size() : 0) +
                ", payment=" + (payment != null ? payment.getPaymentId() : null) + '}';
    }
}
