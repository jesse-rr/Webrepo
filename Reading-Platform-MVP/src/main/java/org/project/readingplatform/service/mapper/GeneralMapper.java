package org.project.readingplatform.service.mapper;

import org.project.readingplatform.dto.*;
import org.project.readingplatform.models.*;
import org.project.readingplatform.models.embedded.Metadata;
import org.project.readingplatform.models.enums.BookStatus;
import org.project.readingplatform.models.enums.ChapterStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static org.project.readingplatform.models.enums.AccountType.FREE;

@Service
public class GeneralMapper {

    public Book toBook(BookDTO request) {
        return Book.builder()
                .title(request.title())
                .authorName(request.authorName())
                .synopsis(request.synopsis())
                .genres(request.genres())
                .bookImg(request.bookImg())
                .rating(0)
                .metadata(new Metadata(0,0,0,0,0,0))
                .chapterStatus(ChapterStatus.NOTHING)
                .chapterStatusTimestamp(null)
                .bookStatus(BookStatus.ON_GOING)
                .isFeatured(false)
                .featuredTimestamp(null)
                .wasRead(false)
                .build();
    }
    public Account generateAccount(User user) {
        return Account.builder()
                .user(user)
                .accountType(FREE)
                .notificationQuantity(0)
                .build();
    }
    public Chapter toChapter(ChapterDTO request, Book book, Integer maxChapterNumberByBookId) {
        return Chapter.builder()
                .book(book)
                .title(request.title())
                .content(request.content())
                .chapterNumber(maxChapterNumberByBookId)
                .build();
    }
    public User toUser(UserDTO request, String encodedPassword) {
        return User.builder()
                .password(encodedPassword)
                .email(request.email())
                .login(request.login())
                .username(request.username())
                .build();
    }
    public Review toReview(double rating, String content, Account account, Book book) {
        return Review.builder()
                .rating(rating)
                .account(account)
                .book(book)
                .content(content)
                .build();
    }
    public Comment toComment(CommentDTO request, Chapter chapter, Account account) {
        return Comment.builder()
                .content(request.content())
                .account(account)
                .chapter(chapter)
                .likes(Set.of())
                .dislikes(Set.of())
                .build();
    }

    public ElasticBook toElasticBook(Book book, List<String> genres) {
        return ElasticBook.builder()
                .title(book.getTitle())
                .authorName(book.getAuthorName())
                .isFeatured(book.isFeatured())
                .metadata(book.getMetadata())
                .bookStatus(book.getBookStatus())
                .genres(genres)
                .rating(book.getRating())
                .wasRead(book.isWasRead())
                .bookId(book.getBookId())
                .build();
    }
}
