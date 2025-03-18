package com.example.auctionhouse.service;

import com.example.auctionhouse.model.Product;
import com.example.auctionhouse.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public Product getProductById(Long productId) {
        log.info("FINDING PRODUCT WITH ID :: {}",productId);
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("PRODUCT NOT FOUND WITH ID :: "+productId));
    }
}
