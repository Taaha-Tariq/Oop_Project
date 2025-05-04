package com.oopsproject.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "car_owners")  // Changed to plural for consistency
@DiscriminatorValue("CAR_OWNER")  // Added discriminator value
public class CarOwner extends Users {
    // Business identifier (not primary key)
    @Column(name = "owner_id", unique = true)
    private Long ownerId;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Car> cars;

    // Default constructor
    public CarOwner() {
    }

    // Parametrized constructor
    public CarOwner(String username, String email, String phoneNumber,
                    String password, Address address, Long ownerId, String firstName, String lastName) {
        super(username, email, phoneNumber, password, "CAR_OWNER", firstName, lastName);
        this.address = address;
        this.ownerId = ownerId;
    }

    // Getters and setters
    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    // Helper methods for bidirectional relationship
    public void addCar(Car car) {
        cars.add(car);
        car.setOwner(this);
    }

    public void removeCar(Car car) {
        cars.remove(car);
        car.setOwner(null);
    }

    @Override
    public String toString() {
        return "CarOwner{" +
                "userId=" + getUserId() +
                ", ownerId=" + ownerId +
                ", address=" + address +
                ", carsCount=" + (cars != null ? cars.size() : 0) +
                '}';
    }
}
//package com.oopsproject.models;
//
//import jakarta.persistence.*;
//        import java.util.List;
//
//// Using the annotation so that this class is mapped to a table in the database
//@Entity
//@Table(name = "car_owner") // The name of the table
//@PrimaryKeyJoinColumn(name = "user_id")
//public class CarOwner extends Users {
//    // This attribute is to be used as the primary key
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO) // It will have auto generated (auto incremented values)
//    private Long ownerId;
//
//    // Many-to-one relationship with Address
//    @ManyToOne
//    @JoinColumn(name = "address_id", nullable = false)
//    private Address address;
//
//    // One-to-many relationship with Car
//    @OneToMany(mappedBy = "owner")
//    private List<Car> cars;
//
//    // Default constructor
//    public CarOwner() {
//    }
//
//    // Parametrized constructor
//    public CarOwner(String username, String email, String phoneNumber,
//                    String password, String role, Address address) {
//        super(username, email, phoneNumber, password, role);
//        this.address = address;
//    }
//
//    // Getters for the field attributes
//    public Long getOwnerId() {
//        return ownerId;
//    }
//
//    public Address getAddress() {
//        return address;
//    }
//
//    public List<Car> getCars() {
//        return cars;
//    }
//
//    // Setters for the field attributes
//    public void setOwnerId(Long ownerId) {
//        this.ownerId = ownerId;
//    }
//
//    public void setAddress(Address address) {
//        this.address = address;
//    }
//
//    public void setCars(List<Car> cars) {
//        this.cars = cars;
//    }
//
//    // Formatting the object information
//    @Override
//    public String toString() {
//        return "CarOwner{" + "ownerId=" + ownerId + ", address=" + address + '}';
//    }
//}