package com.example.auctionhouse.service;

import com.example.auctionhouse.config.JsonUtil;
import com.example.auctionhouse.dto.AccountParamsRequest;
import com.example.auctionhouse.model.Account;
import com.example.auctionhouse.model.User;
import com.example.auctionhouse.model.embedded.AccountType;
import com.example.auctionhouse.model.embedded.Address;
import com.example.auctionhouse.repository.AccountRepository;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.internal.build.AllowSysOut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final NotificationService notificationService;

    public void createAccount(User user) {
        log.info("CREATING ACCOUNT WITH USER ID :: {}",user.getUserId());
        accountRepository.save(Account.builder()
                .accountType(AccountType.UNVERIFIED)
                .address(new Address())
                .user(user)
                .build()
        );
    }

    @Transactional
    public void upgradePlan() {
    }

    public Account updateAccountParams(Long id, AccountParamsRequest request) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ACCOUNT ALTERATION FAILED - NOT FOUND ACCOUNT :: "+id));

        if (request.accountType() != null) { account.setAccountType(request.accountType()); };
        if (request.address() != null) { account.setAddress(request.address()); };
        if (request.profileImage() != null) { account.setProfileImage(request.profileImage()); }

        Map<String, Object> configs = JsonUtil.jsonToMap(account.getConfigs());
        configs.putAll(request.configs());
        account.setConfigs(JsonUtil.mapToJson(configs));

        return accountRepository.save(account);
    }

    public Account getAccount(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ACCOUNT NOT FOUND ACCOUNT :: "+id));
    }

    public void deleteAccount(Long id, String passwd) throws MessagingException {
        Account account = getAccount(id);
        if (account.getUser().getPassword().equals(passwd)) {
            notificationService.sendDeletionRequestNotification(account.getUser().getEmail());
        }
    }

    @Async
    @Scheduled(cron = "0 0 6 * * SUN")
    protected void weeklyCleanupAccounts() {
        accountRepository.deleteAccountsByDeletionTimestamp(LocalDateTime.now());
    }
}
