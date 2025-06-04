package com.example.product.controller;

import com.example.product.dto.ProductRequestDTO;
import com.example.product.model.Product;
import com.example.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add")
    public void addProduct(
            @RequestBody @Valid ProductRequestDTO request
    ) {
        productService.addProduct(request);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Product> getProductById(
            @PathVariable(value = "id") Long productId
    ) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @PutMapping("/alter/{id}")
    public void alterProductById(
            @PathVariable(value = "id") Long productId,
            @RequestBody @Valid ProductRequestDTO request
    ) {
        productService.alterProductById(productId, request);
    }
}
