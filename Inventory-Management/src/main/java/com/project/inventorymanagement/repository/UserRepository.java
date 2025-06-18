package com.project.inventorymanagement.repository;

import com.project.inventorymanagement.model.Location;
import com.project.inventorymanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
