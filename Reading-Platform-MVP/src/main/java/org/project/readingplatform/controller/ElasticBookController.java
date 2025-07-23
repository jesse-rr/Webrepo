package org.project.readingplatform.controller;

import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHost;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.project.readingplatform.models.ElasticBook;
import org.project.readingplatform.models.enums.BookGenre;
import org.project.readingplatform.models.enums.BookStatus;
import org.project.readingplatform.models.enums.ReturnOption;
import org.project.readingplatform.repository.ElasticsearchBookRepository;
import org.project.readingplatform.service.ElasticsearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/elastic_book")
public class ElasticBookController {

    private final ElasticsearchService elasticsearchService;
    private final ElasticsearchBookRepository elasticsearchBookRepository;

    @GetMapping("/search")
    public ResponseEntity<List<ElasticBook>> searchBooksByUserQuery(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String authorName,
            @RequestParam(required = false) List<BookGenre> genres,
            @RequestParam(required = false) BookStatus bookStatus,
            @RequestParam(required = false) Boolean isFeatured,
            @RequestParam(required = false) Boolean wasRead,
            @RequestParam(required = false) Integer minViews,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) ReturnOption returnOption
    ) {
        return ResponseEntity.ok(elasticsearchService.filterBooksBy(title,authorName,genres,bookStatus,isFeatured,wasRead,minViews,minRating,returnOption));
    }

    @PostMapping("/update")
    public void update()  {
        elasticsearchService.syncRepositories();
    }

    @DeleteMapping("/delete")
    public void delete() {
        elasticsearchBookRepository.deleteAll();
    }
}
