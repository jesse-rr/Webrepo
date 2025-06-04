package com.example.gymmanagement.repository;

import com.example.gymmanagement.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.deletionTimestamp <= :timestamp")
    void deleteAllScheduledUsers(LocalDateTime timestamp);
}
