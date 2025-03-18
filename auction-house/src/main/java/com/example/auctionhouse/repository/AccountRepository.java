package com.example.auctionhouse.repository;

import com.example.auctionhouse.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Transactional
    @Query("DELETE FROM account a WHERE a.deletionTimestamp < :timestamp")
    void deleteAccountsByDeletionTimestamp(LocalDateTime timestamp);
}
