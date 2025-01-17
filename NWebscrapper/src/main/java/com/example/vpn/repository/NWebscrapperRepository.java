package com.example.vpn.repository;

import com.example.vpn.models.Website;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NWebscrapperRepository extends JpaRepository<Website, Long> {

    void deleteByUrl(String url);

    Optional<Website> findByUrl(String url);
}
