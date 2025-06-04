package com.example.product.service;

import com.example.product.dto.ProductDetails;
import com.example.product.dto.ProductRequestDTO;
import com.example.product.model.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public Product toProduct(ProductRequestDTO request) {
        return Product.builder()
                .name(request.name())
                .currency(request.currency())
                .description(request.description())
                .initialPrice(request.initialPrice())
                .finalPrice(request.initialPrice())
                .minimalBid(request.minimalBid())
                .quantity(request.quantity())
                .userId(request.userId())
                .categories(request.categories()).build();
    }

    public ProductDetails toProductDetails(Product product) {
        return new ProductDetails(
                product.getName(),
                product.getDescription(),
                product.getInitialPrice(),
                product.getFinalPrice(),
                product.getQuantity(),
                product.getCurrency(),
                product.getCategories(),
                product.getTags()
        );
    }
}
