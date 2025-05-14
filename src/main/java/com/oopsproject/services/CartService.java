package com.oopsproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oopsproject.models.Cart;
import com.oopsproject.models.CartItem;
import com.oopsproject.models.Product;
import com.oopsproject.repositories.CartItemRepository;
import com.oopsproject.repositories.CartRepository;
import com.oopsproject.repositories.ProductRepository;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CarOwnerService carOwnerService;
    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartService(CartRepository cartRepository, ProductRepository productRepository, CarOwnerService carOwnerService, CartItemRepository cartItemRepository) {
        this.carOwnerService = carOwnerService;
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public Cart getCartByCarOwnerId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    @Transactional
    public void addProductToCart(Long userId, int productId) {
        // Find the cart for the user
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            // Create a new cart if it doesn't exist
            cart = new Cart();
            cart.setUser(carOwnerService.getCarOwnerById(userId));
            cart = cartRepository.save(cart);
        }

        // Find the product
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setStockQuantity(product.getStockQuantity() - 1);
        productRepository.save(product);
        // Check if the product is already in the cart
        CartItem existingCartItem = cartItemRepository.findByCartAndProduct(cart, product);
        if (existingCartItem != null) {
            // Increment the quantity if the product is already in the cart
            existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
            cartItemRepository.save(existingCartItem);
        } else {
            // Create a new CartItem
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart); // Set the cart
            cartItem.setProduct(product);
            cartItem.setQuantity(1); // Default quantity
            cartItemRepository.save(cartItem);
        }
    }

    //use to save cart
    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

    // Remove the cart entirely
    @Transactional
    public void removeCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new RuntimeException("Cart not found"));
        cartItemRepository.deleteAll(cart.getCartItems());
        cartRepository.delete(cart);
    }

    // Remove a specific product from the cart
    @Transactional
    public void removeProductFromCart(Long userId, int productId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, productRepository.findById(productId).orElse(null));
            if (cartItem != null) {
                cartItemRepository.delete(cartItem);
            }
        }
    }
}
