package com.oopsproject.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type")
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToOne(mappedBy = "user")
    private Cart cart;

    // Constructors
    public Users() {
    }

    public Users(String username, String email, String phoneNumber, String password, String role) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username=" + username +
                ", email=" + email +
                ", phoneNumber=" + phoneNumber +
                ", role=" + role + '}';
    }
}
//package com.oopsproject.models;
//
//import jakarta.persistence.*;
//        import java.util.List;
//
//// Using the annotation so that this class is mapped to a table in the database
//@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
//@Table(name = "users") // The name of the table
//public class Users {
//    // This attribute is to be used as the primary key
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO) // It will have auto generated (auto incremented values)
//    private Long userId;
//
//    // Defining the attribute username which must be unique and can't be null
//    @Column(unique = true, nullable = false)
//    private String username;
//
//    // Defining the attribute email which must be unique and can't be null
//    @Column(unique = true, nullable = false)
//    private String email;
//
//    // Defining the attribute phoneNumber
//    @Column
//    private String phoneNumber;
//
//    // Defining the attribute password which can't be null
//    @Column(nullable = false)
//    private String password;
//
//    // Defining the attribute role which can't be null
//    @Column(nullable = false)
//    private String role;
//
//    // One-to-many relationship with Order
//    @OneToMany(mappedBy = "user")
//    private List<Order> orders;
//
//    // One-to-one relationship with Cart
//    @OneToOne(mappedBy = "user")
//    private Cart cart;
//
//    // Default constructor
//    public Users() {
//    }
//
//    // Parametrized constructor
//    public Users(String username, String email, String phoneNumber, String password, String role) {
//        this.username = username;
//        this.email = email;
//        this.phoneNumber = phoneNumber;
//        this.password = password;
//        this.role = role;
//    }
//
//    // Getters for the field attributes
//    public Long getUserId() {
//        return userId;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public String getRole() {
//        return role;
//    }
//
//    public List<Order> getOrders() {
//        return orders;
//    }
//
//    public Cart getCart() {
//        return cart;
//    }
//
//    // Setters for the field attributes
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }
//
//    public void setOrders(List<Order> orders) {
//        this.orders = orders;
//    }
//
//    public void setCart(Cart cart) {
//        this.cart = cart;
//    }
//
//    // Formatting the object information
//    @Override
//    public String toString() {
//        return "User{" + "userId=" + userId + ", username=" + username + ", email=" + email +
//                ", phoneNumber=" + phoneNumber + ", role=" + role + '}';
//    }
//}