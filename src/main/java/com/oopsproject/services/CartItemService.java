package com.oopsproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oopsproject.models.CartItem;
import com.oopsproject.repositories.CartItemRepository;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;
 
    @Autowired
    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public CartItem getCartItemByProductId(Long productId) {
        return cartItemRepository.findByProductId(productId);
    }

    public void addCartItem(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }

    public void removeCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    public CartItem getCartItemById(Long cartItemId) {
        return cartItemRepository.findById(cartItemId).orElse(null);
    }

    public CartItem getCartItemByProductIdAndUserId(int productId, Long userId) {
        return cartItemRepository.findByProductIdAndUserId(productId, userId);
    }
}
