package com.project.inventorymanagement.repository;

import com.project.inventorymanagement.model.Category;
import com.project.inventorymanagement.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
