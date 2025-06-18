package com.project.inventorymanagement.repository;

import com.project.inventorymanagement.model.Category;
import com.project.inventorymanagement.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
