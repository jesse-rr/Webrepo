package com.example.gymmanagement.repository;

import com.example.gymmanagement.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
