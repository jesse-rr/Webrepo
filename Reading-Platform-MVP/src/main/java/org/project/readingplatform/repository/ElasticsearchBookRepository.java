package org.project.readingplatform.repository;

import org.project.readingplatform.models.ElasticBook;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticsearchBookRepository extends ElasticsearchRepository<ElasticBook, Long> {
}
