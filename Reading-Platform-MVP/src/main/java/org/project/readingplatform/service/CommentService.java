package org.project.readingplatform.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.readingplatform.dto.CommentDTO;
import org.project.readingplatform.dto.ReviewDTO;
import org.project.readingplatform.models.Book;
import org.project.readingplatform.models.Comment;
import org.project.readingplatform.models.Review;
import org.project.readingplatform.repository.*;
import org.project.readingplatform.service.mapper.GeneralMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

import static org.project.readingplatform.models.enums.AccountType.FREE;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final ReviewRepository reviewRepository;
    private final CommentRepository commentRepository;
    private final AccountRepository accountRepository;
    private final ChapterRepository chapterRepository;
    private final BookRepository bookRepository;
    private final GeneralMapper mapper;

    // ------------------------------------------------------------------------------------------------------------------------------------
    public void addReview(ReviewDTO request) {
        log.info("ADDING REVIEW OF VALUE :: <{}>", request.rating());
        var account = accountRepository.findById(request.accountUUID())
                .orElseThrow(() -> new EntityNotFoundException(String.format("ACCOUNT NOT FOUND WITH ID :: <%s>", request.accountUUID())));
        if (request.content().length() > 255 && account.getAccountType()==FREE) {
            throw new RuntimeException("CONTENT CANNOT EXCEED 255 CHARS");
        }
        var book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("BOOK NOT FOUND WITH ID :: <%s>", request.bookId())));

        alterTotalRating(true, book, reviewRepository.save(mapper.toReview(
                        request.rating(),
                        request.content(),
                        account,
                        book)
                ));

        book.getMetadata().setReviewQuantity(book.getMetadata().getReviewQuantity()+1);
        bookRepository.save(book);
    }

    public void deleteReviewById(Long reviewId) {
        log.info("DELETING REVIEW WITH IF :: <{}>",reviewId);
        var review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("REVIEW NOT FOUND WITH ID :: <%s>",reviewId)));

        review.getBook().getMetadata().setReviewQuantity(review.getBook().getMetadata().getReviewQuantity()-1);
        bookRepository.save(review.getBook());

        alterTotalRating(false, review.getBook(), review);
        reviewRepository.deleteById(reviewId);
    }
    // ------------------------------------------------------------------------------------------------------------------------------------

    public void addComment(CommentDTO request) {
        log.info("ADDING COMMENT OF BODY :: <{}>", request.content());
        var account = accountRepository.findById(request.accountUUID())
                .orElseThrow(() -> new EntityNotFoundException(String.format("ACCOUNT NOT FOUND WITH ID :: <%s>", request.accountUUID())));
        if (request.content().length() > 255 && account.getAccountType()==FREE) {
            throw new RuntimeException("CONTENT CANNOT EXCEED 255 CHARS");
        }
        var chapter = chapterRepository.findById(request.chapterId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("CHAPTER NOT FOUND WITH ID :: <%s>", request.chapterId())));
        commentRepository.save(mapper.toComment(
                request,
                chapter,
                account));

        chapter.getBook().getMetadata().setCommentQuantity(chapter.getBook().getMetadata().getCommentQuantity()+1);
        bookRepository.save(chapter.getBook());
    }

    public void deleteCommentById(Long commentId) {
        log.info("DELETING COMMENT WITH ID :: <{}>",commentId);
        commentRepository.findById(commentId)
                        .ifPresent(comment -> {
                            comment.getChapter().getBook().getMetadata().setCommentQuantity(comment.getChapter().getBook().getMetadata().getCommentQuantity()-1);
                            bookRepository.save(comment.getChapter().getBook());
                        });
        commentRepository.deleteById(commentId);
    }

    public void addLikeDislike(UUID accountUUID, Long commentId, boolean isLike) {
        log.info("ADDING LIKE/DISLIKE TO COMMENT <{}>", commentId);
        commentRepository.findById(commentId)
                .ifPresent(request -> {
                    itExists(request.getLikes(), request.getDislikes(), accountUUID);
                    if (isLike) {
                        request.getLikes().add(accountUUID);
                    } else {
                        request.getDislikes().add(accountUUID);
                    }
                    commentRepository.save(request);
                });
    }

    public void itExists(Set<UUID> likes, Set<UUID> dislikes, UUID accountUUID)  {
        likes.remove(accountUUID);
        dislikes.remove(accountUUID);
    }

    public void addResponseComment(Long commentId, UUID accountUUID, String content) {
        log.info("ADDING RESPONSE COMMENT OF BODY :: <{}, {}>", commentId, content);
        var account = accountRepository.findById(accountUUID)
                .orElseThrow(() -> new EntityNotFoundException(String.format("ACCOUNT NOT FOUND WITH ID :: <%s>", accountUUID)));
        if (content.length() > 255 && account.getAccountType()==FREE) {
            throw new RuntimeException("CONTENT CANNOT EXCEED 255 CHARS");
        }
        var comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("COMMENT NOT FOUND WITH ID :: <%s>", commentId)));
        comment.getComments().add(
                commentRepository.save(
                        Comment.builder()
                                .content(content)
                                .account(account)
                                .chapter(comment.getChapter())
                                .build()
                ));
        commentRepository.save(comment);

        comment.getChapter().getBook().getMetadata().setCommentQuantity(comment.getChapter().getBook().getMetadata().getCommentQuantity()+1);
        bookRepository.save(comment.getChapter().getBook());
    }

    @Async
    public void alterTotalRating(boolean isAdding_Removing, Book book, Review review) {
        double totalAmount = 0;
        if (isAdding_Removing) {
            book.getReviews().add(review);
        } else {
            book.getReviews().remove(review);
        }
        for (Review reviews : book.getReviews()) {
            totalAmount += reviews.getRating();
        }
        book.setRating(totalAmount/book.getReviews().size());
        bookRepository.save(book);
    }
}
