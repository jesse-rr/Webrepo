package com.example.cacophony.repository;

import com.example.cacophony.model.User;
import com.example.cacophony.model.helper.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u.passwordHash FROM User u WHERE u.username=:username")
    String findPasswordByUsername(String username);

    @Query("SELECT u.email FROM User u WHERE u.username=:username")
    String findEmailByUsername(String username);

    Optional<User> findByUsername(String username);

    @Modifying
    @Query("UPDATE User u SET u.deletionTimestamp=:timestamp WHERE u.username=:username")
    void scheduleDeletion(String username, LocalDateTime timestamp);

    @Modifying
    @Query("DELETE FROM User u WHERE u.deletionTimestamp IS NOT NULL AND u.deletionTimestamp <= :now")
    void batchDeleteScheduledAccounts(LocalDateTime now);

    @Modifying
    @Query("UPDATE User u SET u.status=:status WHERE u.userId=:userId")
    void updateUserStatusById(UserStatus status, Long userId);

    @Query("SELECT CASE WHEN u.createdAt >: timestamp THEN true ELSE false END FROM User u WHERE u.username=:username")
    boolean isOldEnought(String username, LocalDateTime timestamp);
}
