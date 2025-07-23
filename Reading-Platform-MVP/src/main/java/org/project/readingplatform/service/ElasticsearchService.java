package org.project.readingplatform.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestClient;
import org.project.readingplatform.models.ElasticBook;
import org.project.readingplatform.models.enums.BookGenre;
import org.project.readingplatform.models.enums.BookStatus;
import org.project.readingplatform.models.enums.ReturnOption;
import org.project.readingplatform.repository.BookRepository;
import org.project.readingplatform.repository.ElasticsearchBookRepository;
import org.project.readingplatform.service.mapper.GeneralMapper;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ElasticsearchService {

    private final ElasticsearchBookRepository elasticsearchBookRepository;
    private final BookRepository bookRepository;
    private final GeneralMapper mapper;
    private final ElasticsearchTemplate elasticsearchTemplate;

    public List<ElasticBook> filterBooksBy(String title, String authorName, List<BookGenre> genres, BookStatus bookStatus, Boolean isFeatured, Boolean wasRead, Integer minViews, Double minRating, ReturnOption returnOption) {
        Criteria criteria = new Criteria();
        if (title!=null && !title.isEmpty()) {
            criteria = criteria
                    .and(new Criteria("title").fuzzy(title));
        }
        if (authorName != null && !authorName.isEmpty()) {
            criteria = criteria
                    .and(new Criteria("authorName").fuzzy(authorName));
        }
        if (genres != null && !genres.isEmpty()) {
            for (BookGenre genre : genres) {
                criteria = criteria.and(new Criteria("genres").is(genre.name()));
            }
        }
        if (bookStatus != null) {
            criteria = criteria.and(new Criteria("bookStatus").is(bookStatus));
        }
        if (isFeatured != null) {
            criteria = criteria.and(new Criteria("isFeatured").is(isFeatured));
        }
        if (wasRead != null) {
            criteria = criteria.and(new Criteria("wasRead").is(wasRead));
        }
        if (minViews != null) {
            criteria = criteria.and(new Criteria("metadata.viewsQuantity").greaterThanEqual(minViews));
        }
        if (minRating != null) {
            criteria = criteria.and(new Criteria("rating").greaterThanEqual(minRating));
        }

        CriteriaQuery query = new CriteriaQuery(criteria);

        if (returnOption != null) {
            switch (returnOption) {
                case VIEWS:
                    query.addSort(Sort.by(Sort.Order.desc("views")));
                    break;
                case BOOKMARKS:
                    query.addSort(Sort.by(Sort.Order.desc("bookmarks")));
                    break;
                case READ_LATER:
                    query.addSort(Sort.by(Sort.Order.desc("readLater")));
                    break;
                case CHAPTER_QUANTITY:
                    query.addSort(Sort.by(Sort.Order.desc("chapterQuantity")));
                    break;
                case REVIEW_QUANTITY:
                    query.addSort(Sort.by(Sort.Order.desc("reviewQuantity")));
                    break;
                case COMMENTS_QUANTITY:
                    query.addSort(Sort.by(Sort.Order.desc("commentsQuantity")));
                    break;
                case RATING:
                    query.addSort(Sort.by(Sort.Order.desc("rating")));
                    break;
                default:
                    throw new RuntimeException("RETURN OPTION INVALID");
            }
        }

        SearchHits<ElasticBook> hits = elasticsearchTemplate.search(query, ElasticBook.class);

        return hits.getSearchHits()
                .stream()
                .map(SearchHit::getContent)
                .toList();
    }

    // ---------------------------------------- SCHEDULED
    @Scheduled(cron = "0 0 * * * MON")
    public void syncRepositories() {
        log.info("SYNCHRONIZING ELASTIC DB TO NORMAL DB");
        bookRepository.getAllBooksByLastModifiedAfter(LocalDateTime.now().minusWeeks(1))
                .forEach(book -> {
                    List<String> genres = book.getGenres().stream()
                            .map(Enum::name)
                            .toList();
                    elasticsearchBookRepository.save(mapper.toElasticBook(book,genres));
                });
    }
    // ---------------------------------------- SCHEDULED
}
