package org.project.readingplatform.repository;

import jakarta.transaction.Transactional;
import org.project.readingplatform.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    @Modifying
    @Transactional
    @Query("DELETE FROM account a WHERE a.deletionTimestamp <= :time")
    void deleteAllAccountUUIDForDeletion(LocalDateTime time);

    @Modifying
    @Transactional
    @Query("UPDATE account a SET a.isVerified=true WHERE a.user.email= :email")
    void updateAccountAfterEmailVerification(String email);
}
