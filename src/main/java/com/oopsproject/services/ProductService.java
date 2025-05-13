package com.oopsproject.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oopsproject.dto.ProductCategoryDTO;
import com.oopsproject.dto.ProductImageDTO;
import com.oopsproject.dto.ProductSaveDTO;
import com.oopsproject.models.Product;
import com.oopsproject.models.ProductCategory;
import com.oopsproject.models.ProductImage;
import com.oopsproject.models.ShopOwner;
import com.oopsproject.repositories.ProductRepository;
import com.oopsproject.repositories.ProductCategoryRepository;
import jakarta.transaction.Transactional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    // In ProductService.java
    @Transactional
    public Product addProduct(Product product) {
        // First save/find the category
        if (product.getCategory() != null) {
            ProductCategory category;
            
            // If the category has an ID, try to find it
            if (product.getCategory().getProductCategoryId() > 0) {
                category = productCategoryRepository.findById(product.getCategory().getProductCategoryId())
                    .orElseGet(() -> productCategoryRepository.save(product.getCategory()));
            } else {
                // Try to find by name
                category = productCategoryRepository.findByCategoryType(product.getCategory().getCategoryType());
                
                // If not found, save it
                if (category == null) {
                    category = productCategoryRepository.save(product.getCategory());
                }
            }
            
            // Set the managed category back to product
            product.setCategory(category);
        }
        
        // Now save the product
        return productRepository.save(product);
    }

    public List<Product> getProductByShopOwner(ShopOwner shopOwner) {
        return productRepository.findByShopOwner(shopOwner);
        
    }

    public ProductSaveDTO convertToDTO(Product product) {
        ProductSaveDTO dto = new ProductSaveDTO();
        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStockQuantity());
        dto.setCategory(product.getCategory() != null ? convertToProductCategoryDTO(product.getCategory()) : null);
        dto.setImages(product.getImages() != null ? convertToProductImageDTOList(product.getImages()) : null);
        
        // Include only the necessary ShopOwner information
        if (product.getShopOwner() != null) {
            dto.setShopOwnerId(product.getShopOwner().getUserId());
            dto.setShopName(product.getShopOwner().getShopName());
        }
        
        return dto;
    }
    
    private ProductCategoryDTO convertToProductCategoryDTO(ProductCategory productCategory) {
        ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
        productCategoryDTO.setProductCategoryId(productCategory.getProductCategoryId());
        productCategoryDTO.setCategoryType(productCategory.getCategoryType());
        return productCategoryDTO;
    }

    private List<ProductImageDTO> convertToProductImageDTOList(List<ProductImage> productImages) {
        if (productImages == null) {
            return null;
        }
        List<ProductImageDTO> productImageDTOs = new ArrayList<>();
        for (ProductImage productImage : productImages) {
            ProductImageDTO productImageDTO = new ProductImageDTO();
            productImageDTO.setImageId(productImage.getImageId());
            productImageDTO.setImageUrl(productImage.getImageUrl());
            productImageDTOs.add(productImageDTO);
        }
        return productImageDTOs;
    }

    public List<ProductSaveDTO> convertToDTOList(List<Product> products) {
        return products.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public Product convertToEntity(ProductSaveDTO dto) {
        Product product = new Product();
        product.setProductId(dto.getProductId());
        product.setProductName(dto.getProductName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStock());
        product.setCategory(dto.getCategory() != null ? convertToProductCategoryEntity(dto.getCategory()) : null);
        product.setImages(dto.getImages() != null ? convertToProductImageEntityList(dto.getImages()) : null);
        // ShopOwner will be set separately
        
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

    public Product getProductById(int productId) {
        return productRepository.findById(productId)
            .orElse(null);
    }

    public void deleteProduct(int productId) {
        productRepository.deleteById(productId);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product findById(int productId) {
        return productRepository.findById(productId)
            .orElse(null);
    }
}
