package com.example.product.service;

import com.example.product.config.KafkaProducerProduct;
import com.example.product.config.NotificationProduct;
import com.example.product.dto.ProductDetails;
import com.example.product.dto.ProductRequestDTO;
import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final KafkaProducerProduct kafkaProducerProduct;

    public void addProduct(ProductRequestDTO request) {
        log.info("ADDING PRODUCT WITH DTO :: {}",request);
        productRepository.save(
                productMapper.toProduct(request)
        );

        kafkaProducerProduct.sendProductNotificationRequest(
                new NotificationProduct(

                )
        );
    }

    public Product getProductById(Long productId) {
        log.info("RETRIEVING PRODUCT WITH ID :: {}",productId);
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("PRODUCT NOT FOUND WITH ID :: "+productId));
    }

    public void alterProductById(Long productId, ProductRequestDTO request) {
        log.info("ALTERING PRODUCT BY ID :: {}", productId);
        Product product = getProductById(productId);
        product.setCategories(request.categories());
        product.setCurrency(request.currency());
        product.setDescription(request.description());
        product.setName(request.name());
        product.setCurrency(request.currency());
        product.setQuantity(request.quantity());
        product.setMinimalBid(request.minimalBid());
        product.setInitialPrice(request.initialPrice());
        productRepository.save(product);

        kafkaProducerProduct.sendProductNotificationRequest(
                new NotificationProduct(

                )
        );
    }

    public void removeProduct(Long productId) {
        log.info("REMOVING PRODUCT - ID :: {}",productId);
        productRepository.updateProductToRemove(productId);

        kafkaProducerProduct.sendProductNotificationRequest(
                new NotificationProduct(

                )
        );
    }

    public ProductDetails getProductDetails(Long productId) {
        log.info("RETRIEVING PRODUCT DETAILS FROM ID :: {}",productId);
        return productMapper.toProductDetails(getProductById(productId));
    }

    public List<Product> getProductsByCategories(List<String> categories, int pageNumber) {
        log.info("RETRIEVING PRODUCT BY CATEGORIES :: {}",categories);
        Pageable pageable = (Pageable) PageRequest.of(pageNumber,20);
        return productRepository.findProductsByCategories(categories, pageable);
    }

    public List<String> getProductsInAuction(int pageNumber) {
        Pageable pageable = (Pageable) PageRequest.of(pageNumber, 20);
        return productRepository.findProductsInAuction(pageable);
    }
}
