package com.oopsproject.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.oopsproject.dto.ProductSaveDTO;
import com.oopsproject.models.Product;
import com.oopsproject.models.ShopOwner;
import com.oopsproject.services.ProductService;
import com.oopsproject.services.ShopOwnerService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ShopOwnerService shopOwnerService;

    public ProductController(ProductService productService, ShopOwnerService shopOwnerService) {
        this.productService = productService;
        this.shopOwnerService = shopOwnerService;
    }

    // Endpoint to add a new product
    // In ProductController.java
    @PostMapping("/add")
    public ResponseEntity<ProductSaveDTO> addProduct(@RequestBody ProductSaveDTO productdto, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        // Check if userId is null (not logged in)
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        // Check if the shop owner exists
        ShopOwner shopOwner = shopOwnerService.getShopOwnerById(userId);
        if (shopOwner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        // Debug - check if shopId is populated
        // System.out.println("ShopOwner: " + shopOwner.getUserId() + ", ShopId: " + shopOwner.getShopId());
        
        // Make sure the shop ID is properly set
        if (shopOwner.getShopId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null); // Or return an error message
        }

        Product product = productService.convertToEntity(productdto);
        
        // Set the shop owner for the product
        product.setShopOwner(shopOwner);
        Product savedProduct = productService.addProduct(product);

        ProductSaveDTO savedProductDTO = productService.convertToDTO(savedProduct);
        return new ResponseEntity<>(savedProductDTO, HttpStatus.CREATED);
    }

    @GetMapping("/shopowner")
    public ResponseEntity<List<ProductSaveDTO>> getProductsByShopOwner(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        ShopOwner shopOwner = shopOwnerService.getShopOwnerById(userId);
        if (shopOwner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        List<Product> products = productService.getProductByShopOwner(shopOwner);
        List<ProductSaveDTO> productDTOs = productService.convertToDTOList(products);
        
        return ResponseEntity.ok(productDTOs);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int itemId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        ShopOwner shopOwner = shopOwnerService.getShopOwnerById(userId);
        if (shopOwner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        Product product = productService.getProductById(itemId);
        if (product == null || !product.getShopOwner().getUserId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        productService.deleteProduct(itemId);
        return ResponseEntity.noContent().build();
    }


    // Get all products
    @GetMapping("/all")
    public ResponseEntity<List<ProductSaveDTO>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductSaveDTO> productDTOs = productService.convertToDTOList(products);
        return ResponseEntity.ok(productDTOs);
    }
}
