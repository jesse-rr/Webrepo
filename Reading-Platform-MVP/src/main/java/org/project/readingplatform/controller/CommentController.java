package org.project.readingplatform.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.project.readingplatform.dto.CommentDTO;
import org.project.readingplatform.dto.ReviewDTO;
import org.project.readingplatform.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class CommentController {

    private final CommentService commentService;

    // ------------------------------------------------------
    @PostMapping("/reviews")
    public ResponseEntity<String> addReview(
            @RequestBody @Valid ReviewDTO request
    ) {
        commentService.addReview(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/reviews/{review-id}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable("review-id") Long reviewId
    ) {
        commentService.deleteReviewById(reviewId);
        return ResponseEntity.noContent().build();
    }
    // ------------------------------------------------------

    @PostMapping("/comments")
    public ResponseEntity<String> addComment(
            @RequestBody @Valid CommentDTO request
    ) {
        commentService.addComment(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/comments/delete/{comment-id}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable("comment-id") Long commentId
    ) {
        commentService.deleteCommentById(commentId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/comments/{comment-id}")
    public ResponseEntity<String> addResponseComment(
            @PathVariable("comment-id") Long commentId,
            @RequestParam UUID accountUUID,
            @RequestParam @NotNull String content
    ) {
        commentService.addResponseComment(commentId, accountUUID, content);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/like-dislike/{comment-id}")
    public ResponseEntity<String> addLikeDislike(
            @RequestParam UUID accountUUID,
            @PathVariable("comment-id") Long commentId,
            @RequestParam boolean isLike
    ) {
        commentService.addLikeDislike(accountUUID, commentId, isLike);
        return ResponseEntity.ok().build();
    }
}
