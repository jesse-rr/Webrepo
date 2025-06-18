package com.project.inventorymanagement.repository;

import com.project.inventorymanagement.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query("SELECT DISTINCT p FROM Product p JOIN p.categories c WHERE c.categoryId IN :categoryIds")
    List<Product> findProductsByCategories(@Param("categoryIds") Set<Long> categoryIds);

    Page<Product> findByCategories_IdIn(Set<Long> categoryIds, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCase(String searchTerm, Pageable pageable);

    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.categoryId IN :categoryIds AND LOWER(p.name) LIKE LOWER(concat('%', :searchTerm,'%'))")
    Page<Product> findByCategories_IdInAndNameContainingIgnoreCase(
            @Param("categoryIds") Set<Long> categoryIds,
            @Param("searchTerm") String searchTerm,
            Pageable pageable);
}