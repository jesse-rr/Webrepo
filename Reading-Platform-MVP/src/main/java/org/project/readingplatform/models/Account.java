package org.project.readingplatform.models;

import jakarta.persistence.*;
import lombok.*;
import org.project.readingplatform.models.enums.AccountType;
import org.project.readingplatform.models.enums.Gender;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity(name = "account")
@Table(name = "account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID accountUUID;

    private String accountImg;
    private String bio;
    private String phone;
    private boolean isVerified;
    private LocalDateTime deletionTimestamp;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private int notificationQuantity;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt;


    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Comment> comments;
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Review> reviews;
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Report> reports;
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Book> recommendations;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany
    @JoinTable(name = "account_bookmark",
            uniqueConstraints = @UniqueConstraint(columnNames = {"account_id", "book_id"}),
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> bookmarks;
    @ManyToMany
    @JoinTable(name = "account_history",
            uniqueConstraints = @UniqueConstraint(columnNames = {"account_id", "book_id"}),
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> history;
    @ManyToMany
    @JoinTable(name = "account_readLater",
            uniqueConstraints = @UniqueConstraint(columnNames = {"account_id", "book_id"}),
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> read_later;

}
