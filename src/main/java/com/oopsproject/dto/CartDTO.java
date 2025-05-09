package com.oopsproject.dto;

import java.util.List;

public class CartDTO {
    private Long cartId;
    private List<CartItemDTO> items; // Assuming you have a CartItemDTO class

    // Getters and Setters
    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public List<CartItemDTO> getItems() {
        return items;
    }

    public void setItems(List<CartItemDTO> items) {
        this.items = items;
    }
}
