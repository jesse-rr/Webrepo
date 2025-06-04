package com.example.product.repository;

import com.example.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Query("UPDATE product p SET p.isRemoved= true WHERE p.productId= :productId")
    void updateProductToRemove(Long productId);

    @Query("SELECT p FROM product p WHERE p.categories IN :categories")
    List<Product> findProductsByCategories(List<String> categories, Pageable pageable);

    @Query("SELECT p FROM product p WHERE p.auctionId IS NOT NULL")
    List<String> findProductsInAuction(Pageable pageable);
}
