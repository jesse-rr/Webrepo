package org.project.readingplatform.repository;

import jakarta.transaction.Transactional;
import org.project.readingplatform.models.Book;
import org.project.readingplatform.models.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface ChapterRepository extends JpaRepository<Chapter, Long> {

    @Query("SELECT MAX(c.chapterNumber) FROM chapter c WHERE c.book= :book")
    Integer findMaxChapterNumberFromChapterWithBook(Book book);

    @Modifying
    @Transactional
    @Query("DELETE chapter c WHERE c.book.bookId = :bookId AND c.chapterNumber = :chapterNumber")
    void deleteChapterByNumber(Long bookId, int chapterNumber);

    @Modifying
    @Transactional
    @Query("UPDATE chapter c SET c.chapterNumber = c.chapterNumber - 1 WHERE c.book.bookId = :bookId AND c.chapterNumber > :removedChapterNumber")
    void updateChapterNumbersAfterRemoval(Long bookId, int removedChapterNumber);

    @Modifying
    @Transactional
    @Query("UPDATE chapter c SET c.chapterNumber= c.chapterNumber + 1 WHERE c.book.bookId= :bookId AND c.chapterNumber >= :addedChapterNumber")
    void updateChapterNumbersAfterAdding(Long bookId, int addedChapterNumber);
}
