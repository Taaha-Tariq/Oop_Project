package com.oopsproject.services;

import com.oopsproject.dto.CartItemDTO;
import com.oopsproject.dto.OrderResponseDTO;
import com.oopsproject.dto.ProductDTO;
import com.oopsproject.models.*;
import com.oopsproject.repositories.OrderRepository;
import com.oopsproject.repositories.UsersRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UsersRepository userRepository;
    private final CartService cartService;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        UsersRepository userRepository,
                        CartService cartService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartService = cartService;
    }

    @Transactional
    public OrderResponseDTO createOrderFromCart(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartService.getCartByCarOwnerId(userId);
        if (cart == null || cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order(user, new Date(), "PLACED");
        List<OrderItem> orderItems = new ArrayList<>();
        List<CartItemDTO> cartItemDTOs = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItems()) {
            // Create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());

            orderItems.add(orderItem);

            // Prepare CartItemDTO for response
            CartItemDTO itemDTO = new CartItemDTO();
            itemDTO.setCartItemId(cartItem.getCartItemId());
            itemDTO.setQuantity(cartItem.getQuantity());

            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductId(cartItem.getProduct().getProductId());
            productDTO.setProductName(cartItem.getProduct().getProductName());
            productDTO.setPrice(cartItem.getProduct().getPrice());

            itemDTO.setProduct(productDTO);
            cartItemDTOs.add(itemDTO);
        }

        order.setOrderItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        // Clear cart
        cart.getCartItems().clear();
        cartService.saveCart(cart);

        // Prepare response DTO
        OrderResponseDTO responseDTO = new OrderResponseDTO();
        responseDTO.setOrderId(savedOrder.getId());
        responseDTO.setOrderDate(savedOrder.getOrderDate());
        responseDTO.setStatus(savedOrder.getStatus());
        responseDTO.setItems(cartItemDTOs);

        return responseDTO;
    }

    public List<OrderResponseDTO> getAllOrdersByUserId(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Order> orders = orderRepository.findByUser(user);

        return orders.stream().map(order -> {
            OrderResponseDTO dto = new OrderResponseDTO();
            dto.setOrderId(order.getId());
            dto.setOrderDate(order.getOrderDate());
            dto.setStatus(order.getStatus());

            List<CartItemDTO> itemDTOs = order.getOrderItems().stream().map(orderItem -> {
                CartItemDTO itemDTO = new CartItemDTO();
                itemDTO.setQuantity(orderItem.getQuantity());

                ProductDTO productDTO = new ProductDTO();
                productDTO.setProductId(orderItem.getProduct().getProductId());
                productDTO.setProductName(orderItem.getProduct().getProductName());
                productDTO.setPrice(orderItem.getProduct().getPrice());

                itemDTO.setProduct(productDTO);
                return itemDTO;
            }).collect(Collectors.toList());

            dto.setItems(itemDTOs);
            return dto;
        }).collect(Collectors.toList());
    }
}
