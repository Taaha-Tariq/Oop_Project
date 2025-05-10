package com.oopsproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.oopsproject.models.*;
import com.oopsproject.dto.*;
import com.oopsproject.repositories.AddressRepository;
import com.oopsproject.repositories.CarOwnerRepository;
import com.oopsproject.repositories.CityRepository;
import com.oopsproject.repositories.CountryRepository;

@Service
public class CarOwnerService {
    private final CarOwnerRepository carOwnerRepository;
    private final PasswordService passwordService;
    private final AddressRepository addressRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository; 

    @Autowired
    public CarOwnerService(CarOwnerRepository carOwnerRepository, PasswordService passwordService, AddressRepository addressRepository, CityRepository cityRepository, CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
        this.carOwnerRepository = carOwnerRepository;
        this.passwordService = passwordService;
        this.addressRepository = addressRepository;
        this.cityRepository = cityRepository;
    }

    public CarOwner saveCarOwner(CarOwner carOwner) {
        // Save the address first if it's new
        if (carOwner.getAddress() != null && carOwner.getAddress().getAddressId() == null) {
            // Check if the address has a city
            if (carOwner.getAddress().getCity() == null) {
                throw new IllegalArgumentException("Address must have a city");
            } else {
                // If city is provided but not saved yet (transient)
                City city = carOwner.getAddress().getCity();
                if (city.getCityId() == null) {
                    // Check if the city has a country
                    if (city.getCountry() == null) {
                        throw new IllegalArgumentException("City must have a country");
                    } else {
                        // If country is provided but not saved yet (transient)
                        Country country = city.getCountry();
                        if (country.getCountryId() == null) {
                            // Check if country already exists with the same name
                            Country existingCountry = countryRepository.findByCountryName(country.getCountryName());
                            if (existingCountry != null) {
                                city.setCountry(existingCountry);
                            } else {
                                // Save the country first if it doesn't exist
                                Country savedCountry = countryRepository.save(country);
                                city.setCountry(savedCountry);
                            }
                        }
                    }
                    
                    // Check if city already exists with the same name in the same country
                    City existingCity = cityRepository.findByCityNameAndCountry_CountryId(
                        city.getCityName(), city.getCountry().getCountryId());
                        
                    if (existingCity != null) {
                        carOwner.getAddress().setCity(existingCity);
                    } else {
                        // Save the city after country is handled
                        City savedCity = cityRepository.save(city);
                        carOwner.getAddress().setCity(savedCity);
                    }
                }

                Address existingAddress = addressRepository.findByStreetAndCity_CityId(
                    carOwner.getAddress().getStreet(), carOwner.getAddress().getCity().getCityId());

                if (existingAddress != null) {
                    carOwner.setAddress(existingAddress);
                } else {
                    // Save the address after city is handled
                    Address savedAddress = addressRepository.save(carOwner.getAddress());
                    carOwner.setAddress(savedAddress);
                }
            }
        
        // Save the address after city is handled
        Address savedAddress = addressRepository.save(carOwner.getAddress());
        carOwner.setAddress(savedAddress);
        }
    
        // Hash the password before saving
        String hashedPassword = passwordService.hashPassword(carOwner.getPassword());
        carOwner.setPassword(hashedPassword);
        
        return carOwnerRepository.save(carOwner);
    }

    public CarOwner convertToCarOwnerEntity(CarOwnerDTO carOwnerDTO) {
        CarOwner carOwner = new CarOwner();
        carOwner.setUserId(carOwnerDTO.getUserId());
        carOwner.setUsername(carOwnerDTO.getUsername());
        carOwner.setEmail(carOwnerDTO.getEmail());
        carOwner.setPhoneNumber(carOwnerDTO.getPhoneNumber());
        carOwner.setPassword(carOwnerDTO.getPassword());
        carOwner.setAddress(carOwnerDTO.getAddress() != null ? convertToAddressEntity(carOwnerDTO.getAddress()) : null);
        carOwner.setFirstName(carOwnerDTO.getFirstName());
        carOwner.setLastName(carOwnerDTO.getLastName());
        carOwner.setCars(carOwnerDTO.getCar() != null ? convertToCarEntityList(carOwnerDTO.getCar()) : null);
        carOwner.setOrders(carOwnerDTO.getOrders() != null ? convertToOrderEntityList(carOwnerDTO.getOrders()) : null);
        carOwner.setCart(carOwnerDTO.getCart() != null ? convertToCartEntity(carOwnerDTO.getCart()) : null);

        carOwner.setRole("CAR_OWNER");

        return carOwner;
    }

    public Cart convertToCartEntity(CartDTO cartDTO) {
        if (cartDTO == null) {
            return null;
        }
        Cart cart = new Cart();
        cart.setCartId(cartDTO.getCartId());
        cart.setCartItems(cartDTO.getItems() != null ? convertToCartItemEntityList(cartDTO.getItems()) : null);
        return cart;
    }

    private List<CartItem> convertToCartItemEntityList(List<CartItemDTO> cartItemDTOs) {
        List<CartItem> cartItems = new ArrayList<>();
        for (CartItemDTO cartItemDTO : cartItemDTOs) {
            CartItem cartItem = new CartItem();
            cartItem.setCartItemId(cartItemDTO.getCartItemId());
            cartItem.setQuantity(cartItemDTO.getQuantity());
            cartItem.setProduct(cartItemDTO.getProduct() != null ? convertToProductEntity(cartItemDTO.getProduct()) : null);
            cartItems.add(cartItem);
        }
        return cartItems;
    }

    private Product convertToProductEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setProductId(productDTO.getProductId());
        product.setProductName(productDTO.getProductName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setStockQuantity(productDTO.getStockQuantity());
        product.setCategory(productDTO.getCategory() != null ? convertToProductCategoryEntity(productDTO.getCategory()) : null);
        product.setImages(productDTO.getImages() != null ? convertToProductImageEntityList(productDTO.getImages()) : null);
        return product;
    }

    private List<ProductImage> convertToProductImageEntityList(List<ProductImageDTO> productImageDTOs) {
        List<ProductImage> productImages = new ArrayList<>();
        for (ProductImageDTO productImageDTO : productImageDTOs) {
            ProductImage productImage = new ProductImage();
            productImage.setImageId(productImageDTO.getImageId());
            productImage.setImageUrl(productImageDTO.getImageUrl());
            productImages.add(productImage);
        }
        return productImages;
    }

    private ProductCategory convertToProductCategoryEntity(ProductCategoryDTO productCategoryDTO) {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(productCategoryDTO.getProductCategoryId());
        productCategory.setCategoryType(productCategoryDTO.getCategoryType());
        return productCategory;
    }

    public List<Order> convertToOrderEntityList(List<OrderDTO> orderDTOs) {
        List<Order> orders = new ArrayList<>();
        for (OrderDTO orderDTO : orderDTOs) {
            Order order = new Order();
            order.setId(orderDTO.getOrderId());
            order.setOrderDate(orderDTO.getOrderDate());
            order.setStatus(orderDTO.getStatus());
            order.setPayment(orderDTO.getPayment() != null ? convertToPaymentEntity(orderDTO.getPayment()) : null);
            order.setOrderItems(orderDTO.getOrderItems() != null ? convertToOrderItemEntityList(orderDTO.getOrderItems()) : null);
            orders.add(order);
        }
        return orders;
    }

    private Payment convertToPaymentEntity(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setPaymentId(paymentDTO.getPaymentId());
        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentDate(paymentDTO.getPaymentDate());
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        payment.setPaymentStatus(paymentDTO.getPaymentStatus());
        return payment;
    }

    private List<OrderItem> convertToOrderItemEntityList(List<OrderItemDTO> orderItemDTOs) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDTO orderItemDTO : orderItemDTOs) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderItemId(orderItemDTO.getOrderItemId());
            orderItem.setQuantity(orderItemDTO.getQuantity());
            orderItem.setProduct(orderItemDTO.getProduct() != null ? convertToProductEntity(orderItemDTO.getProduct()) : null);
            orderItem.setPrice(orderItemDTO.getPrice());
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    public List<Car> convertToCarEntityList(List<CarDTO> carDTOs) {
        List<Car> cars = new ArrayList<>();
        for (CarDTO carDTO : carDTOs) {
            Car car = new Car();
            car.setCarId(carDTO.getCarId());
            car.setModel(carDTO.getModel());
            car.setYear(carDTO.getYear());
            car.setName(carDTO.getName());
            car.setCategory(carDTO.getCategory() != null ? convertToCategoryEntity(carDTO.getCategory()) : null);
            car.setCompany(carDTO.getCompany() != null ? convertToCompanyEntity(carDTO.getCompany()) : null);
            car.setMaintenanceReminders(carDTO.getMaintenanceReminders() != null ? convertToMaintenanceReminderEntityList(carDTO.getMaintenanceReminders()) : null);
            car.setMaintenanceHistory(carDTO.getMaintenanceHistory() != null ? convertToMaintenanceHistoryEntityList(carDTO.getMaintenanceHistory()) : null);
            cars.add(car);
        }
        return cars;
    }

    private List<MaintenanceHistory> convertToMaintenanceHistoryEntityList(List<MaintenanceHistoryDTO> maintenanceHistoryDTOs) {
        List<MaintenanceHistory> maintenanceHistories = new ArrayList<>();
        for (MaintenanceHistoryDTO maintenanceHistoryDTO : maintenanceHistoryDTOs) {
            MaintenanceHistory maintenanceHistory = new MaintenanceHistory();
            maintenanceHistory.setMaintenanceId(maintenanceHistoryDTO.getMaintenanceId());
            maintenanceHistory.setDate(maintenanceHistoryDTO.getDate());
            maintenanceHistory.setDescription(maintenanceHistoryDTO.getDescription());
            maintenanceHistory.setCost(maintenanceHistoryDTO.getCost());
            maintenanceHistory.setMileage(maintenanceHistoryDTO.getMileage());
            maintenanceHistories.add(maintenanceHistory);
        }
        return maintenanceHistories;
    }

    private List<MaintenanceReminder> convertToMaintenanceReminderEntityList(List<MaintenanceReminderDTO> maintenanceReminderDTOs) {
        List<MaintenanceReminder> maintenanceReminders = new ArrayList<>();
        for (MaintenanceReminderDTO maintenanceReminderDTO : maintenanceReminderDTOs) {
            MaintenanceReminder maintenanceReminder = new MaintenanceReminder();
            maintenanceReminder.setReminderId(maintenanceReminderDTO.getReminderId());
            maintenanceReminder.setReminderDate(maintenanceReminderDTO.getReminderDate());
            maintenanceReminder.setDescription(maintenanceReminderDTO.getDescription());
            maintenanceReminder.setDueMileage(maintenanceReminderDTO.getDueMileage());
            maintenanceReminders.add(maintenanceReminder);
        }
        return maintenanceReminders;
    }

    private Company convertToCompanyEntity(CompanyDTO companyDTO) {
        Company company = new Company();
        company.setCompanyId(companyDTO.getCompanyId());
        company.setCompanyName(companyDTO.getCompanyName());
        return company;
    }

    private Category convertToCategoryEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setCategoryId(categoryDTO.getCategoryId());
        category.setType(categoryDTO.getType());
        return category;
    }

    public Address convertToAddressEntity(AddressDTO addressDTO) {
        Address address = new Address();
        address.setAddressId(addressDTO.getAddressId());
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity() != null ? convertToCityEntity(addressDTO.getCity()) : null);
        return address;
    }

    private City convertToCityEntity(CityDTO cityDTO) {
        City city = new City();
        city.setCityId(cityDTO.getCityId());
        city.setCityName(cityDTO.getCityName());
        city.setCountry(cityDTO.getCountry() != null ? convertToCountryEntity(cityDTO.getCountry()) : null);
        return city;
    }

    private Country convertToCountryEntity(CountryDTO countryDTO) {
        Country country = new Country();
        country.setCountryId(countryDTO.getCountryId());
        country.setCountryName(countryDTO.getCountryName());
        return country;
    }

    public CarOwnerDTO convertToCarOwnerDTO(CarOwner carOwner) {
        CarOwnerDTO carOwnerDTO = new CarOwnerDTO();
        carOwnerDTO.setUserId(carOwner.getUserId());
        carOwnerDTO.setUsername(carOwner.getUsername());
        carOwnerDTO.setEmail(carOwner.getEmail());
        carOwnerDTO.setPhoneNumber(carOwner.getPhoneNumber());
        carOwnerDTO.setPassword(carOwner.getPassword());
        carOwnerDTO.setAddress(carOwner.getAddress() != null ? convertToAddressDTO(carOwner.getAddress()) : null);
        carOwnerDTO.setFirstName(carOwner.getFirstName());
        carOwnerDTO.setLastName(carOwner.getLastName());
        carOwnerDTO.setCar(carOwner.getCars() != null ? convertToCarDTOList(carOwner.getCars()) : null);
        carOwnerDTO.setOrders(carOwner.getOrders() != null ? convertToOrderDTOList(carOwner.getOrders()) : null);
        carOwnerDTO.setCart(carOwner.getCart() != null ? convertToCartDTO(carOwner.getCart()) : null);
        return carOwnerDTO;
    }

    private List<OrderDTO> convertToOrderDTOList(List<Order> orders) {
        List<OrderDTO> orderDTOs = new ArrayList<>();
        for (Order order : orders) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrderId(order.getId());
            orderDTO.setOrderDate(order.getOrderDate());
            orderDTO.setStatus(order.getStatus());
            orderDTO.setPayment(order.getPayment() != null ? convertToPaymentDTO(order.getPayment()) : null);
            orderDTO.setOrderItems(order.getOrderItems() != null ? convertToOrderItemDTOList(order.getOrderItems()) : null);
            orderDTOs.add(orderDTO);
        }
        return orderDTOs;
    }

    private PaymentDTO convertToPaymentDTO(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPaymentId(payment.getPaymentId());
        paymentDTO.setAmount(payment.getAmount());
        paymentDTO.setPaymentDate(payment.getPaymentDate());
        paymentDTO.setPaymentMethod(payment.getPaymentMethod());
        paymentDTO.setPaymentStatus(payment.getPaymentStatus());
        return paymentDTO;
    }

    private List<OrderItemDTO> convertToOrderItemDTOList(List<OrderItem> orderItems) {
        List<OrderItemDTO> orderItemDTOs = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            OrderItemDTO orderItemDTO = new OrderItemDTO();
            orderItemDTO.setOrderItemId(orderItem.getOrderItemId());
            orderItemDTO.setQuantity(orderItem.getQuantity());
            orderItemDTO.setProduct(orderItem.getProduct() != null ? convertToProductDTO(orderItem.getProduct()) : null);
            orderItemDTO.setPrice(orderItem.getPrice());
            orderItemDTOs.add(orderItemDTO);
        }
        return orderItemDTOs;
    }

    private CartDTO convertToCartDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(cart.getCartId());
        cartDTO.setItems(cart.getCartItems() != null ? convertToCartItemDTOList(cart.getCartItems()) : null);
        return cartDTO;
    }

    private List<CartItemDTO> convertToCartItemDTOList(List<CartItem> cartItems) {
        List<CartItemDTO> cartItemDTOs = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            CartItemDTO cartItemDTO = new CartItemDTO();
            cartItemDTO.setCartItemId(cartItem.getCartItemId());
            cartItemDTO.setQuantity(cartItem.getQuantity());
            cartItemDTO.setProduct(cartItem.getProduct() != null ? convertToProductDTO(cartItem.getProduct()) : null);
            cartItemDTOs.add(cartItemDTO);
        }
        return cartItemDTOs;
    }

    private ProductDTO convertToProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(product.getProductId());
        productDTO.setProductName(product.getProductName());
        productDTO.setPrice(product.getPrice());
        productDTO.setDescription(product.getDescription());
        productDTO.setStockQuantity(product.getStockQuantity());
        productDTO.setCategory(product.getCategory() != null ? convertToProductCategoryDTO(product.getCategory()) : null);
        productDTO.setImages(product.getImages() != null ? convertToProductImageDTOList(product.getImages()) : null);
        productDTO.setShopOwner(product.getShopOwner() != null ? convertToShopOwnerDTO(product.getShopOwner()) : null);
        return productDTO;
    }

    private ProductCategoryDTO convertToProductCategoryDTO(ProductCategory productCategory) {
        ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
        productCategoryDTO.setProductCategoryId(productCategory.getProductCategoryId());
        productCategoryDTO.setCategoryType(productCategory.getCategoryType());
        return productCategoryDTO;
    }

    private ShopOwnerDTO convertToShopOwnerDTO(ShopOwner shopOwner) {
        ShopOwnerDTO shopOwnerDTO = new ShopOwnerDTO();
        shopOwnerDTO.setShopId(shopOwner.getShopId());
        shopOwnerDTO.setShopName(shopOwner.getShopName());
        shopOwnerDTO.setShopPhoneNumber(shopOwner.getShopPhoneNumber());
        shopOwnerDTO.setAddress(shopOwner.getAddress() != null ? convertToAddressDTO(shopOwner.getAddress()) : null);
        return shopOwnerDTO;
    }

    private List<ProductImageDTO> convertToProductImageDTOList(List<ProductImage> productImages) {
        List<ProductImageDTO> productImageDTOs = new ArrayList<>();
        for (ProductImage productImage : productImages) {
            ProductImageDTO productImageDTO = new ProductImageDTO();
            productImageDTO.setImageId(productImage.getImageId());
            productImageDTO.setImageUrl(productImage.getImageUrl());
            productImageDTOs.add(productImageDTO);
        }
        return productImageDTOs;
    }

    private List<CarDTO> convertToCarDTOList(List<Car> cars) {
        List<CarDTO> carDTOs = new ArrayList<>();
        for (Car car : cars) {
            CarDTO carDTO = new CarDTO();
            carDTO.setCarId(car.getCarId());
            carDTO.setModel(car.getModel());
            carDTO.setYear(car.getYear());
            carDTO.setName(car.getName());
            carDTO.setCategory(car.getCategory() != null ? convertToCategoryDTO(car.getCategory()) : null);
            carDTO.setCompany(car.getCompany() != null ? convertToCompanyDTO(car.getCompany()) : null);
            carDTO.setMaintenanceReminders(car.getMaintenanceReminders() != null ? convertToMaintenanceReminderDTOList(car.getMaintenanceReminders()) : null);
            carDTO.setMaintenanceHistory(car.getMaintenanceHistory() != null ? convertToMaintenanceHistoryDTOList(car.getMaintenanceHistory()) : null);
            carDTOs.add(carDTO);
        }
        return carDTOs;
    }

    private List<MaintenanceHistoryDTO> convertToMaintenanceHistoryDTOList(List<MaintenanceHistory> maintenanceHistories) {
        List<MaintenanceHistoryDTO> maintenanceHistoryDTOs = new ArrayList<>();
        for (MaintenanceHistory maintenanceHistory : maintenanceHistories) {
            MaintenanceHistoryDTO maintenanceHistoryDTO = new MaintenanceHistoryDTO();
            maintenanceHistoryDTO.setMaintenanceId(maintenanceHistory.getMaintenanceId());
            maintenanceHistoryDTO.setDate(maintenanceHistory.getDate());
            maintenanceHistoryDTO.setDescription(maintenanceHistory.getDescription());
            maintenanceHistoryDTO.setCost(maintenanceHistory.getCost());
            maintenanceHistoryDTO.setMileage(maintenanceHistory.getMileage());
            maintenanceHistoryDTOs.add(maintenanceHistoryDTO);
        }   
        return maintenanceHistoryDTOs;
    }

    private List<MaintenanceReminderDTO> convertToMaintenanceReminderDTOList(List<MaintenanceReminder> maintenanceReminders) {
        List<MaintenanceReminderDTO> maintenanceReminderDTOs = new ArrayList<>();
        for (MaintenanceReminder maintenanceReminder : maintenanceReminders) {
            MaintenanceReminderDTO maintenanceReminderDTO = new MaintenanceReminderDTO();
            maintenanceReminderDTO.setReminderId(maintenanceReminder.getReminderId());
            maintenanceReminderDTO.setReminderDate(maintenanceReminder.getReminderDate());
            maintenanceReminderDTO.setDescription(maintenanceReminder.getDescription());
            maintenanceReminderDTO.setDueMileage(maintenanceReminder.getDueMileage());
            maintenanceReminderDTOs.add(maintenanceReminderDTO);
        }
        return maintenanceReminderDTOs;
    }

    private CategoryDTO convertToCategoryDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(category.getCategoryId());
        categoryDTO.setType(category.getType());
        return categoryDTO;
    }

    private CompanyDTO convertToCompanyDTO(Company company) {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setCompanyId(company.getCompanyId());
        companyDTO.setCompanyName(company.getCompanyName());
        return companyDTO;
    }

    private AddressDTO convertToAddressDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setAddressId(address.getAddressId());
        addressDTO.setStreet(address.getStreet());
        addressDTO.setCity(address.getCity() != null ? convertToCityDTO(address.getCity()) : null);
        return addressDTO;
    }

    private CityDTO convertToCityDTO(City city) {
        CityDTO cityDTO = new CityDTO();
        cityDTO.setCityId(city.getCityId());
        cityDTO.setCityName(city.getCityName());
        cityDTO.setCountry(city.getCountry() != null ? convertToCountryDTO(city.getCountry()) : null);
        return cityDTO;
    }

    private CountryDTO convertToCountryDTO(Country country) {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setCountryId(country.getCountryId());
        countryDTO.setCountryName(country.getCountryName());
        return countryDTO;
    }
    
    public CarOwner authenticate(String email, String password) {
        CarOwner carOwner = carOwnerRepository.findByEmail(email);
        if (carOwner != null && passwordService.verifyPassword(password, carOwner.getPassword())) {
            return carOwner;
        }
        return null;
    }

    public CarOwner getCarOwnerById(Long userId) {
        return carOwnerRepository.findById(userId).orElse(null);
    }

    @Transactional
    public CarOwner updateCarOwner(CarOwner carOwner, CarOwnerDTO carOwnerDTO) {
        // Update the car owner details
        carOwner.setUsername(carOwnerDTO.getUsername());
        carOwner.setEmail(carOwnerDTO.getEmail());
        carOwner.setPhoneNumber(carOwnerDTO.getPhoneNumber());
        carOwner.setFirstName(carOwnerDTO.getFirstName());
        carOwner.setLastName(carOwnerDTO.getLastName());
        carOwner.setPassword(carOwnerDTO.getPassword());
        
        // Update the address if provided
        if (carOwnerDTO.getAddress() != null) {
            Address address = convertToAddressEntity(carOwnerDTO.getAddress());

            if (address.getCity() != null) {
                City city = convertToCityEntity(carOwnerDTO.getAddress().getCity());
                City persistentCity;

                if (city.getCountry() != null) {
                    Country country = convertToCountryEntity(carOwnerDTO.getAddress().getCity().getCountry());
                    Country persistentCountry;
                    Country existingCountry = countryRepository.findByCountryName(country.getCountryName());
                    if (existingCountry != null) {
                        persistentCountry = existingCountry;
                    } else {
                        persistentCountry = countryRepository.save(country);
                    }
                    city.setCountry(persistentCountry);
                }

                City existingCity = cityRepository.findByCityNameAndCountry_CountryId(
                    city.getCityName(), city.getCountry().getCountryId());
                if (existingCity != null) {
                    persistentCity = existingCity;
                } else {
                    persistentCity = cityRepository.save(city);
                }

                address.setCity(persistentCity);
            }

            Address existingAddress = addressRepository.findByStreetAndCity_CityId(
                address.getStreet(), address.getCity().getCityId());   
            Address persistentAddress;
            if (existingAddress != null) {
                persistentAddress = existingAddress;
            } else {
                persistentAddress = addressRepository.save(address);
            }

            carOwner.setAddress(persistentAddress);
        }

        // FIX: Properly handle the cars collection with orphanRemoval=true
        if (carOwnerDTO.getCar() != null) {
            // Get existing cars to compare
            List<Car> existingCars = carOwner.getCars();
            if (existingCars == null) {
                existingCars = new ArrayList<>();
                carOwner.setCars(existingCars);
            }
            
            // Create map of existing cars by ID for quick lookup
            Map<Integer, Car> existingCarsMap = new HashMap<>();
            for (Car car : existingCars) {
                existingCarsMap.put(car.getCarId(), car);
            }
            
            // Track which cars to keep
            Set<Integer> processedCarIds = new HashSet<>();
            // Process cars from DTO
            for (CarDTO carDTO : carOwnerDTO.getCar()) {
                if (existingCarsMap.containsKey(carDTO.getCarId())) {
                    // Update existing car
                    Car existingCar = existingCarsMap.get(carDTO.getCarId());
                    existingCar.setModel(carDTO.getModel());
                    existingCar.setYear(carDTO.getYear());
                    existingCar.setName(carDTO.getName());
                    // Update other properties
                    
                    processedCarIds.add(carDTO.getCarId());
                } else {
                    // Add new car
                    Car newCar = convertToCarEntity(carDTO);
                    newCar.setOwner(carOwner);
                    existingCars.add(newCar);
                    
                    
                    processedCarIds.add(newCar.getCarId());      
                }
            }
            
            // Remove cars that aren't in the DTO anymore
            existingCars.removeIf(car -> !processedCarIds.contains(car.getCarId()));
        }

        carOwner.setOrders(carOwnerDTO.getOrders() != null ? convertToOrderEntityList(carOwnerDTO.getOrders()) : null);
        carOwner.setCart(carOwnerDTO.getCart() != null ? convertToCartEntity(carOwnerDTO.getCart()) : null);
        carOwner.setRole("CAR_OWNER");
        carOwner.setPassword(carOwnerDTO.getPassword());

        return carOwnerRepository.save(carOwner);
    }

    private Car convertToCarEntity(CarDTO carDTO) {
        Car car = new Car();
        car.setCarId(carDTO.getCarId());
        car.setModel(carDTO.getModel());
        car.setYear(carDTO.getYear());
        car.setName(carDTO.getName());
        car.setCategory(carDTO.getCategory() != null ? convertToCategoryEntity(carDTO.getCategory()) : null);
        car.setCompany(carDTO.getCompany() != null ? convertToCompanyEntity(carDTO.getCompany()) : null);
        return car;
    }
}