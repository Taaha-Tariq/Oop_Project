package com.oopsproject.controllers;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oopsproject.dto.CartDTO;
import com.oopsproject.dto.CartItemDTO;
import com.oopsproject.models.Cart;
import com.oopsproject.services.CarOwnerService;
import com.oopsproject.services.CartService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final CarOwnerService carOwnerService;

    @Autowired
    public CartController(CartService cartService, CarOwnerService carOwnerService) {
        this.cartService = cartService;
        this.carOwnerService = carOwnerService;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<String> addToCart(@PathVariable int productId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }

        try {
            cartService.addProductToCart(userId, productId);
            return ResponseEntity.ok("Product added to cart");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<CartDTO> getCart(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        try {
            Cart cart = cartService.getCartByCarOwnerId(userId);
            CartDTO cartDTO = new CartDTO();
            cartDTO.setCartId(cart.getCartId());
            cartDTO.setItems(cart.getCartItems() != null ? cart.getCartItems().stream().map(item -> {
                CartItemDTO itemDTO = new CartItemDTO();
                itemDTO.setProduct(item.getProduct() != null ? carOwnerService.convertToProductDTO(item.getProduct()) : null);
                itemDTO.setQuantity(item.getQuantity());
                return itemDTO;
            }).collect(Collectors.toList()) : new ArrayList<>()); 

            return ResponseEntity.ok(cartDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> removeFromCart(@PathVariable int productId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }

        try {
            cartService.removeProductFromCart(userId, productId);
            return ResponseEntity.ok("Product removed from cart");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
