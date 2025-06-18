package com.project.inventorymanagement.repository;

import com.project.inventorymanagement.model.Category;
import com.project.inventorymanagement.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
