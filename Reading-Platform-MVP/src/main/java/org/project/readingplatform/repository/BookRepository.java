package org.project.readingplatform.repository;

import jakarta.transaction.Transactional;
import org.project.readingplatform.models.Book;
import org.project.readingplatform.models.enums.BookGenre;
import org.project.readingplatform.models.enums.ChapterStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b.bookId FROM book b ORDER BY RANDOM() LIMIT 1")
    Long getRandomBookId();

    @Query("SELECT b.bookId FROM book b WHERE b.isFeatured = true")
    List<Long> getAllFeaturedBooks();

    @Query("SELECT b FROM book b WHERE b.modifiedAt >= :timestamp")
    List<Book> getAllBooksByLastModifiedAfter(LocalDateTime timestamp);

    @Query("SELECT b FROM book b JOIN b.genres g WHERE b.wasRead = false AND g IN :genres ORDER BY RANDOM()")
    List<Book> findUnreadBooksWithGenres(List<BookGenre> genres);

    @Query("SELECT b FROM book b WHERE b.authorName IN :authorNames AND b.wasRead = false ORDER BY RANDOM()")
    List<Book> findUnreadBooksFromAuthor(List<String> authorNames);

    @Modifying
    @Transactional
    @Query("UPDATE book b SET b.chapterStatus= :status WHERE b.chapterStatusTimestamp <= :timestamp")
    void updateChapterStatusAfterWeek(ChapterStatus status, LocalDateTime timestamp);

    @Modifying
    @Transactional
    @Query("UPDATE book b SET b.isFeatured= false WHERE b.featuredTimestamp <= :timestamp")
    void updateFeatureAfterMonth(LocalDateTime timestamp);
}
