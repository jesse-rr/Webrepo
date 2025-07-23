package org.project.readingplatform.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.readingplatform.dto.BookDTO;
import org.project.readingplatform.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addBook(
            @RequestBody @Valid List<BookDTO> request
    ) {
        bookService.addBook(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/random")
    public ResponseEntity<Long> randomBookRecommendation() {
        return ResponseEntity.ok(bookService.randomBookRecommendation());
    }
}
