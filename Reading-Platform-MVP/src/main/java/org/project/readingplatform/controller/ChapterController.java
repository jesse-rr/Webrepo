package org.project.readingplatform.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.readingplatform.dto.ChapterDTO;
import org.project.readingplatform.service.ChapterService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chapters")
@PreAuthorize("hasRole('ADMIN')")
public class ChapterController {

    private final ChapterService chapterService;

    @PostMapping("/{book-id}")
    public ResponseEntity<String> addChapter(
            @PathVariable("book-id") Long bookId,
            @RequestBody @Valid List<ChapterDTO> request
    ) {
        chapterService.addChapter(bookId, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{book-id}/{chapter-number}")
    public ResponseEntity<String> addChapterByNumber(
            @PathVariable("book-id") Long bookId,
            @PathVariable("chapter-number") int chapterNumber,
            @RequestBody @Valid ChapterDTO request
    ) {
        chapterService.addChapterByNumber(bookId, chapterNumber, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{book-id}")
    public ResponseEntity<Void> removeChapter(
            @PathVariable("book-id") Long bookId,
            @RequestParam int chapterNumber
    ) {
        chapterService.removeChapter(bookId, chapterNumber);
        return ResponseEntity.noContent().build();
    }
}
