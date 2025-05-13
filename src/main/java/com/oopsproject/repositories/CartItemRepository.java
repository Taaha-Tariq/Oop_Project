package com.oopsproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.oopsproject.models.CartItem;
import com.oopsproject.models.Cart;
import com.oopsproject.models.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // Custom query to find a cart item by product ID
    @Query("SELECT ci FROM CartItem ci WHERE ci.product.productId = :productId")
    CartItem findByProductId(@Param("productId") Long productId);
 
    // Custom query to find a cart item by product ID and user ID
    @Query("SELECT ci FROM CartItem ci WHERE ci.product.productId = :productId AND ci.cart.user.userId = :userId")
    CartItem findByProductIdAndUserId(@Param("productId") int productId, @Param("userId") Long userId);

    // Custom query to find a cart item by cart and product
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart = :cart AND ci.product = :product")
    CartItem findByCartAndProduct(@Param("cart") Cart cart, @Param("product") Product product);
}
