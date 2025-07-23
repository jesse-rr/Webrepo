package org.project.readingplatform.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity(name = "review")
@Table(
        name = "review",
        uniqueConstraints = @UniqueConstraint(columnNames = {"book_id","account_id"})
)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Size(max = 999, message = "REPORT MUST BE WITHIN 999 CHARS")
    private String content;
    private double rating;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @OneToMany(mappedBy = "review", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Report> reports;
}
