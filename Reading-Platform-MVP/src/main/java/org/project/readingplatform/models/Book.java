package org.project.readingplatform.models;

import jakarta.persistence.*;
import lombok.*;
import org.project.readingplatform.models.embedded.Metadata;
import org.project.readingplatform.models.enums.BookGenre;
import org.project.readingplatform.models.enums.BookStatus;
import org.project.readingplatform.models.enums.ChapterStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "book")
@Table(name = "book")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Column(unique = true)
    private String title;
    private String synopsis;
    private String authorName;
    private double rating;
    private String bookImg;
    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;
    @Enumerated(EnumType.STRING)
    private ChapterStatus chapterStatus;
    private LocalDateTime chapterStatusTimestamp;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<BookGenre> genres;

    private boolean isFeatured;
    private LocalDateTime featuredTimestamp;
    private boolean wasRead;

    @Embedded
    private Metadata metadata;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Chapter> chapters;
    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Review> reviews;
    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Report> reports;
    @ManyToMany(mappedBy = "bookmarks")
    private List<Account> accounts;
    @ManyToMany(mappedBy = "history")
    private List<Account> accounts_2;
    @ManyToMany(mappedBy = "read_later")
    private List<Account> accounts_3;
    @ManyToOne
    @JoinColumn(name = "account_id", unique = true)
    private Account account;

}
