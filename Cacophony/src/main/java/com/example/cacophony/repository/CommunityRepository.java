package com.example.cacophony.repository;

import com.example.cacophony.model.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {
}
