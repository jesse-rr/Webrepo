package org.project.readingplatform.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.readingplatform.dto.BookDTO;
import org.project.readingplatform.models.Account;
import org.project.readingplatform.models.Book;
import org.project.readingplatform.models.enums.BookGenre;
import org.project.readingplatform.models.enums.ChapterStatus;
import org.project.readingplatform.repository.AccountRepository;
import org.project.readingplatform.repository.BookRepository;
import org.project.readingplatform.service.mapper.GeneralMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;
    private final GeneralMapper mapper;
    private final AccountRepository accountRepository;

    public void addBook(List<BookDTO> request) {
        log.info("BOOKS ADDED WITH BODY'S :: <{}>",request);
        for (BookDTO dto : request) {
            bookRepository.save(mapper.toBook(dto));
        }
    }

    public Long randomBookRecommendation() {
        log.info("GETTING RANDOM BOOK RECOMMENDATION");
        Long bookId = bookRepository.getRandomBookId();
        if (bookId == null) {
            throw new EntityNotFoundException("BOOK DB IS NULL/ZERO");
        }
        return bookId;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void updateBookToFeatured(Long bookId) {
        log.info("MAKING BOOK 'FEATURED' WITH ID :: <{}>",bookId);
        bookRepository.findById(bookId)
                .ifPresent(book -> {
                    book.setFeatured(true);
                    book.setFeaturedTimestamp(LocalDateTime.now().plusMonths(1));
                    bookRepository.save(book);
                });
    }

    public List<Long> getAllFeaturedBooks() {
        log.info("GETTING ALL FEATURED BOOKS");
        return bookRepository.getAllFeaturedBooks();
    }

    // ---------------------------------------- SCHEDULED
    @Async
    @Scheduled(cron = "0 0 * * * MON")
    public void updatePersonalRecommendations() {
        if (LocalDate.now().getDayOfWeek() != DayOfWeek.MONDAY) {
            throw new IllegalStateException("THIS FUNCTION ONLY WORKS EVERY MONDAY");
        } else {
            List<Account> accounts = accountRepository.findAll();
            accounts.parallelStream().forEach(
                    account -> {
                        if ((account.getBookmarks().isEmpty() && account.getHistory().isEmpty()) ||
                                account.getCreatedAt().isBefore(LocalDateTime.now().minusWeeks(1))) {
                            return;
                        }
                        Set<BookGenre> bookGenres = account.getBookmarks()
                                .stream()
                                .flatMap(book -> book.getGenres().stream())
                                .collect(Collectors.toSet());
                        bookGenres.addAll(account.getHistory()
                                .stream()
                                .flatMap(book -> book.getGenres().stream())
                                .collect(Collectors.toSet()));
                        Set<String> authors = account.getBookmarks()
                                .stream()
                                .map(Book::getAuthorName)
                                .collect(Collectors.toSet());
                        authors.addAll(account.getHistory()
                                .stream()
                                .map(Book::getAuthorName)
                                .collect(Collectors.toSet()));

                        Set<Book> finalRecommendations = new HashSet<>();

                        finalRecommendations.addAll(bookRepository.findUnreadBooksFromAuthor(authors.stream().toList())
                                .stream()
                                .limit(5)
                                .collect(Collectors.toSet()));
                        finalRecommendations.addAll(bookRepository.findUnreadBooksWithGenres(bookGenres.stream().toList())
                                .stream()
                                .limit(20)
                                .collect(Collectors.toSet()));

                        if (finalRecommendations.size() > 25) {
                            finalRecommendations = finalRecommendations.stream().limit(25).collect(Collectors.toSet());
                        }

                        account.setRecommendations(new ArrayList<>(finalRecommendations));
                        accountRepository.save(account);
                    }
            );
        }
    }

    @Scheduled(cron = "0 0 * * * MON")
    public void updateChapterStatusAfterWeek() {
        log.info("UPDATING CHAPTER STATUS");
        bookRepository.updateChapterStatusAfterWeek(ChapterStatus.NOTHING, LocalDateTime.now());
    }

    @Scheduled(cron = "0 0 * * * MON")
    public void updateFeatureTimestampAfterMonth() {
        log.info("UPDATING FEATURE BACK AFTER MONTH");
        bookRepository.updateFeatureAfterMonth(LocalDateTime.now());
    }
    // ---------------------------------------- SCHEDULED
}
