package com.project.inventorymanagement.service;

import com.project.inventorymanagement.dto.ProductRequestDTO;
import com.project.inventorymanagement.model.Product;
import com.project.inventorymanagement.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final SupplierService supplierService;
    private final CategoryService categoryService;

    public void addProduct(ProductRequestDTO productRequestDTO) {
        productRepository.save(mapToProduct(productRequestDTO));
    }

    public void removeProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    public void updateProduct(Long productId, ProductRequestDTO productRequestDTO) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("PRODUCT NOT FOUND WITH ID :: "+productId));

        product.setName(productRequestDTO.name());
        product.setDescription(productRequestDTO.description());
        product.setPrice(productRequestDTO.price());
        product.setSKU(productRequestDTO.SKU());
        product.setSupplier(supplierService.getSupplierById(productRequestDTO.supplierId()));

        productRepository.save(product);
    }

    public void updateProductCategories(Long productId, List<Long> categoryIds) {
        productRepository.findById(productId)
                .ifPresent(product -> {
                    product.setCategories(categoryService.getCategoriesByIds(categoryIds));
                    productRepository.save(product);
                });
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("PRODUCT NOT FOUND WITH ID :: "+productId));
    }

    public Page<Product> getFilteredProducts(Set<Long> categoryIds, String searchTerm, Double minPrice, Double maxPrice, Long supplierId, Pageable pageable) {
        Specification<Product> spec = (root, query, criteriaBuilder) -> null;

        if (categoryIds != null && !categoryIds.isEmpty()) {
            spec = spec.and((root, query, cb) -> root.join("categories").get("id").in(categoryIds));
        }

        if (searchTerm != null && !searchTerm.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + searchTerm.toLowerCase() + "%"));
        }

        if (minPrice != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), minPrice));
        }

        if (maxPrice != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), maxPrice));
        }

        if (supplierId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("supplier").get("id"), supplierId));
        }

        return productRepository.findAll(spec, pageable);
    }

    private Product mapToProduct(ProductRequestDTO productRequestDTO) {
        return Product.builder()
                .name(productRequestDTO.name())
                .description(productRequestDTO.description())
                .price(productRequestDTO.price())
                .SKU(productRequestDTO.SKU())
                .supplier(supplierService.getSupplierById(productRequestDTO.supplierId()))
                .build();
    }
}
