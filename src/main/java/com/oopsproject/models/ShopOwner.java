package com.oopsproject.models;

import jakarta.persistence.*;

@Entity
@Table(name = "shop_owners")
@DiscriminatorValue("SHOP_OWNER")
public class ShopOwner extends Users {
    @Column(name = "shop_id", unique = true)
    private Long shopId;

    @Column(nullable = false)
    private String shopName;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Column(name = "shop_phone_number")
    private String shopPhoneNumber;

    // Constructors
    public ShopOwner() {
    }

    public ShopOwner(String username, String email, String phoneNumber,
                     String password, String shopName, Address address, Long shopId) {
        super(username, email, phoneNumber, password, "SHOP_OWNER");
        this.shopName = shopName;
        this.address = address;
        this.shopId = shopId;
    }

    // Getters and Setters
    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getShopPhoneNumber() {
        return shopPhoneNumber;
    }

    public void setShopPhoneNumber(String shopPhoneNumber) {
        this.shopPhoneNumber = shopPhoneNumber;
    }

    @Override
    public String toString() {
        return "ShopOwner{" +
                "userId=" + getUserId() +
                ", shopId=" + shopId +
                ", shopName=" + shopName +
                ", address=" + address +
                ", shopPhoneNumber=" + shopPhoneNumber + '}';
    }
}
//package com.oopsproject.models;
//
//import jakarta.persistence.*;
//
//// Using the annotation so that this class is mapped to a table in the database
//@Entity
//@Table(name = "shop_owner") // The name of the table
//@PrimaryKeyJoinColumn(name = "user_id")
//public class ShopOwner extends Users {
//    // This attribute is to be used as the primary key
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO) // It will have auto generated (auto incremented values)
//    private Long shopId;
//
//    // Defining the attribute shopName which can't be null
//    @Column(nullable = false)
//    private String shopName;
//
//    // Many-to-one relationship with Address
//    @ManyToOne
//    @JoinColumn(name = "address_id", nullable = false)
//    private Address address;
//
//    // Defining the phone number for the shop
//    @Column
//    private String shopPhoneNumber;
//
//    // Default constructor
//    public ShopOwner() {
//    }
//
//    // Parametrized constructor
//    public ShopOwner(String username, String email, String phoneNumber,
//                     String password, String role, String shopName, Address address) {
//        super(username, email, phoneNumber, password, role);
//        this.shopName = shopName;
//        this.address = address;
//    }
//
//    // Getters for the field attributes
//    public Long getShopId() {
//        return shopId;
//    }
//
//    public String getShopName() {
//        return shopName;
//    }
//
//    public Address getAddress() {
//        return address;
//    }
//
//    public String getShopPhoneNumber() {
//        return shopPhoneNumber;
//    }
//
//    // Setters for the field attributes
//    public void setShopId(Long shopId) {
//        this.shopId = shopId;
//    }
//
//    public void setShopName(String shopName) {
//        this.shopName = shopName;
//    }
//
//    public void setAddress(Address address) {
//        this.address = address;
//    }
//
//    public void setShopPhoneNumber(String shopPhoneNumber) {
//        this.shopPhoneNumber = shopPhoneNumber;
//    }
//
//    // Formatting the object information
//    @Override
//    public String toString() {
//        return "ShopOwner{" + "shopId=" + shopId + ", shopName=" + shopName +
//                ", address=" + address + ", shopPhoneNumber=" + shopPhoneNumber + '}';
//    }
//}