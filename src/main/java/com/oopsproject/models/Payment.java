package com.oopsproject.models;

import jakarta.persistence.*;
import java.util.Date;

// Using the annotation so that this class is mapped to a table in the database
@Entity
@Table(name = "payments") // The name of the table
public class Payment {
    // This attribute is to be used as the primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // It will have auto generated (auto incremented values)
    private Long paymentId;

    // Defining the relationship with Order entity
    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // Defining the attribute paymentDate which can't be null
    @Column(name = "payment_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;

    // Defining the attribute amount which can't be null
    @Column(name = "amount", nullable = false)
    private Float amount;

    // Defining the attribute paymentMethod which is an enumerated type and can't be null
    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    // Defining the attribute status which is an enumerated type and can't be null

    @Column(name = "status", nullable = false)
    private String paymentStatus;

    // Default constructor
    public Payment() {
    }

    // Parametrized constructor
    public Payment(Order order, Date paymentDate, Float amount,
                   String paymentMethod, String paymentStatus) {
        this.order = order;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
    }

    // Getters for the field attributes
    public Long getPaymentId() {
        return paymentId;
    }

    public Order getOrder() {
        return order;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public Float getAmount() {
        return amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    // Setters for the field attributes
    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    // Formatting the object information
    @Override
    public String toString() {
        return "Payment{" + "id=" + paymentId +
                ", order=" + (order != null ? order.getId() : null) +
                ", paymentDate=" + paymentDate +
                ", amount=" + amount +
                ", paymentMethod=" + paymentMethod +
                ", status=" + paymentStatus + '}';
    }
}