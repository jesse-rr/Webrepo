package com.example.eventhorizon.repository;

import com.example.eventhorizon.model.ExternalProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExternalProviderRepository extends JpaRepository<ExternalProvider, String> {
}
