package org.project.readingplatform.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.readingplatform.dto.ChapterDTO;
import org.project.readingplatform.models.Account;
import org.project.readingplatform.models.Book;
import org.project.readingplatform.repository.BookRepository;
import org.project.readingplatform.repository.ChapterRepository;
import org.project.readingplatform.service.mapper.GeneralMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.project.readingplatform.models.enums.ChapterStatus.NEW_CHAPTERS;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChapterService {

    private final ChapterRepository chapterRepository;
    private final BookRepository bookRepository;
    private final GeneralMapper mapper;

    public void addChapter(Long bookId, List<ChapterDTO> request) {
        log.info("ADDING CHAPTER WITH BODY :: <{}>", request);

        var book = bookRepository.findById(bookId)
                .map(book1 -> {
                    book1.setChapterStatus(NEW_CHAPTERS);
                    book1.setChapterStatusTimestamp(LocalDateTime.now().plusWeeks(1));
                    return bookRepository.save(book1);
                })
                .orElseThrow(() -> new EntityNotFoundException(String.format("BOOK NOT FOUND WITH ID :: <%s>", bookId)));

        Integer maxChapterNumber = chapterRepository.findMaxChapterNumberFromChapterWithBook(book);
        int nextChapterNumber = (maxChapterNumber == null) ? 0 : maxChapterNumber + 1;

        for (ChapterDTO dto : request) {
            chapterRepository.save(mapper.toChapter(dto, book, nextChapterNumber));
            nextChapterNumber++;
        }

        book.getMetadata().setChapterQuantity(book.getMetadata().getChapterQuantity() + request.size());
        bookRepository.save(book);

        sendBookmarkNotification(bookId);
    }


    public void addChapterByNumber(Long bookId, int chapterNumber, ChapterDTO request) {
        log.info("ADDING CHAPTER BY NUMBER WITH BODY :: <{}>", request);
        var book = bookRepository.findById(bookId)
                .map(book1 -> {
                    book1.setChapterStatus(NEW_CHAPTERS);
                    book1.setChapterStatusTimestamp(LocalDateTime.now().plusWeeks(1));
                    return bookRepository.save(book1);
                })
                .orElseThrow(() -> new EntityNotFoundException(String.format("BOOK NOT FOUND WITH ID :: <%s>", bookId)));

        var maxChapterNum = chapterRepository.findMaxChapterNumberFromChapterWithBook(book);
        maxChapterNum = (maxChapterNum == null) ? 0 : maxChapterNum + 1;
        if (chapterNumber > maxChapterNum || chapterNumber < 0) {
            throw new ArithmeticException(String.format("CHAPTER NUMBER CANNOT BE NEGATIVE/HIGHER THAN MAX VALUE OF <%s>",maxChapterNum));
        }
        chapterRepository.updateChapterNumbersAfterAdding(bookId, chapterNumber);
        chapterRepository.save(mapper.toChapter(request, book, chapterNumber));

        book.getMetadata().setChapterQuantity(book.getMetadata().getChapterQuantity() + 1);
        bookRepository.save(book);

        sendBookmarkNotification(bookId);
    }


    public void removeChapter(Long bookId, int chapterNumber) {
        log.info("REMOVING CHAPTER WITH BOOK ID :: <{}> :: AND NUMBER :: <{}>",bookId,chapterNumber);
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("BOOK NOT FOUND WITH ID :: <%s>",bookId)));

        if (chapterNumber > chapterRepository.findMaxChapterNumberFromChapterWithBook(book) || chapterNumber < 0) {
            throw new ArithmeticException("CHAPTER NUMBER CANNOT BE NEGATIVE/HIGHER THAN MAX QUANTITY OF BOOK CHAPTERS");
        }

        chapterRepository.deleteChapterByNumber(book.getBookId(), chapterNumber);
        chapterRepository.updateChapterNumbersAfterRemoval(book.getBookId(), chapterNumber);

        book.getMetadata().setChapterQuantity(book.getMetadata().getChapterQuantity()-1);
        bookRepository.save(book);
    }

    @Async
    public void sendBookmarkNotification(Long bookId) {
        bookRepository.findById(bookId)
                .map(Book::getAccounts)
                .ifPresent(accounts -> {
                    for (Account account : accounts) {
                        account.setNotificationQuantity(account.getNotificationQuantity()+1);
                    }
                });
    }
}
