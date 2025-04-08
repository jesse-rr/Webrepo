package com.example.eventhorizon.repository;

import com.example.eventhorizon.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, String> {
}
