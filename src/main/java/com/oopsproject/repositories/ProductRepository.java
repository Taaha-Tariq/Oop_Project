package com.oopsproject.repositories;

import com.oopsproject.models.Product;
import com.oopsproject.models.ProductCategory;
import com.oopsproject.models.ShopOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Custom query to find products by shop owner
    List<Product> findByShopOwner(ShopOwner shopOwner);

    // Custom query to find products by category
    List<Product> findByCategory(ProductCategory category);

    // Custom query to find products by name
    List<Product> findByProductNameContaining(String productName);
    
}
