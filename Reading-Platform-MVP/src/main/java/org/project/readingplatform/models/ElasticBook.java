package org.project.readingplatform.models;

import jakarta.persistence.ElementCollection;
import lombok.*;
import org.project.readingplatform.models.embedded.Metadata;
import org.project.readingplatform.models.enums.BookStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Document(indexName = "elastic_book")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ElasticBook {

    @Id
    private Long bookId;

    @Field(type = FieldType.Text)
    private String title;
    @Field(type = FieldType.Text)
    private String authorName;
    @Field(type = FieldType.Double)
    private double rating;
    @Field(type = FieldType.Keyword)
    private BookStatus bookStatus;
    @Field(type = FieldType.Keyword)
    private List<String> genres;
    @Field(type = FieldType.Boolean)
    private boolean isFeatured;
    @Field(type = FieldType.Boolean)
    private boolean wasRead;
    @Field(type = FieldType.Object)
    private Metadata metadata;

}
