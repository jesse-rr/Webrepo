package com.project.inventorymanagement.repository;

import com.project.inventorymanagement.model.Product;
import com.project.inventorymanagement.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
