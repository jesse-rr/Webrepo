package org.project.readingplatform.service;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.readingplatform.dto.AccountDTO;
import org.project.readingplatform.models.Account;
import org.project.readingplatform.models.Book;
import org.project.readingplatform.models.User;
import org.project.readingplatform.models.enums.AccountChoice;
import org.project.readingplatform.models.enums.AccountType;
import org.project.readingplatform.models.enums.BookGenre;
import org.project.readingplatform.repository.AccountRepository;
import org.project.readingplatform.repository.BookRepository;
import org.project.readingplatform.service.mapper.GeneralMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.project.readingplatform.models.enums.AccountType.PREMIUM;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final BookRepository bookRepository;
    private final EmailService emailService;
    private final RedisService redisService;
    private final GeneralMapper generalMapper;

    public void addAccount(User user) throws MessagingException {
        log.info("ADDING ACCOUNT");
        accountRepository.save(generalMapper.generateAccount(user));
        emailService.sendEmailVerificationCode(user.getEmail());
    }

    public void accountOptions(Long bookId, UUID accountUUID, AccountChoice choice) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("BOOK NOT FOUND WITH ID :: <%s>", bookId)));
        var account = accountRepository.findById(accountUUID)
                .orElseThrow(() -> new EntityNotFoundException(String.format("ACCOUNT NOT FOUND WITH ID :: <%s>", accountUUID)));

        switch (choice) {
            case BOOKMARK:
                log.info("ADDING/REMOVING BOOKMARK TO BOOK WITH ID :: <{}>", bookId);
                if (account.getBookmarks().contains(book)) {
                    account.getBookmarks().remove(book);
                    book.getMetadata().setBookmarksQuantity(book.getMetadata().getBookmarksQuantity() - 1);
                } else {
                    account.getBookmarks().add(book);
                    book.getMetadata().setBookmarksQuantity(book.getMetadata().getBookmarksQuantity() + 1);
                }
                break;
            case READ_LATER:
                log.info("ADDING/REMOVING READ LATER WITH BOOK ID :: <{}>", bookId);
                if (account.getRead_later().contains(book)) {
                    account.getRead_later().remove(book);
                    book.getMetadata().setReadLaterQuantity(book.getMetadata().getReviewQuantity()-1);
                } else {
                    account.getRead_later().add(book);
                    book.getMetadata().setReadLaterQuantity(book.getMetadata().getReviewQuantity()+1);
                }
                break;
            default:
                throw new IllegalArgumentException("NOT ACCEPTABLE CHOICE FOR ACCOUNT");
        }

        bookRepository.save(book);
        accountRepository.save(account);
    }

    public void alterAccount(AccountDTO request) {
        accountRepository.findById(request.accountUUID())
                .map(account -> {
                    account.setAccountImg(request.accountImage());
                    account.setBio(request.bio());
                    account.setGender(request.gender());
                    account.setPhone(request.phone());
                    return accountRepository.save(account);
                })
                .orElseThrow(() -> new EntityNotFoundException(String.format("ACCOUNT NOT FOUND WITH ID :: <%s>",request.accountUUID())));
    }

    @Async
    public void deleteAccount(UUID accountUUID) throws MessagingException {
        var account = accountRepository.findById(accountUUID)
                .map(timestamp -> {
                    if (timestamp.getDeletionTimestamp() == null) {
                        log.info("SETTING DELETION OF ACCOUNT AFTER 24h");
                        timestamp.setDeletionTimestamp(LocalDateTime.now().plusDays(1));
                    } else {
                        log.info("CANCELING DELETION OF ACCOUNT");
                        timestamp.setDeletionTimestamp(null);
                    }
                    return accountRepository.save(timestamp);
                })
                .orElseThrow(() -> new EntityNotFoundException(String.format("ACCOUNT NOT FOUND WITH ID :: <%s>", accountUUID)));

        if (account.getDeletionTimestamp() != null) {
            emailService.sendAccountDeletionVerification(account.getUser().getEmail());
        }
    }

    public void verifyCode(String email, String code) {
        String cachedCode = redisService.getCodeByUserEmail(email);
        if (cachedCode == null) {
            throw new IllegalArgumentException("VERIFICATION CODE EXPIRED");
        }
        if (cachedCode.equals(code)) {
            log.info("ACCOUNT VERIFIED");
            redisService.deleteVerificationCode(email);
            accountRepository.updateAccountAfterEmailVerification(email);
        } else {
            throw new IllegalArgumentException("CODES DO NOT MATCH");
        }
    }
    
    public void makeAccountPremium(UUID accountUUID) {
        log.info("MAKING ACCOUNT PREMIUM");
        accountRepository.findById(accountUUID)
                .ifPresent(account -> {
                    account.setAccountType(PREMIUM);
                    accountRepository.save(account);
                });
    }

    // ---------------------------------------- SCHEDULED
    @Scheduled(cron = "0 0 * * * MON")
    public void checkAndDeleteAccount() {
        accountRepository.deleteAllAccountUUIDForDeletion(LocalDateTime.now());
    }
    // ---------------------------------------- SCHEDULED
}
