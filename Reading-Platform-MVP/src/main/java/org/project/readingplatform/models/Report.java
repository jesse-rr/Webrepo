package org.project.readingplatform.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.project.readingplatform.models.enums.ReportPriority;
import org.project.readingplatform.models.enums.ReportType;

import java.time.LocalDateTime;

@Entity(name = "report")
@Table(name = "report")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @Enumerated(EnumType.STRING)
    private ReportType type;
    @Size(max = 255, message = "REPORT MUST BE WITHIN 255 CHARS")
    private String userInfo;

    @Column(nullable = false, unique = true)
    private String reportNumber;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    @Column(insertable = false)
    private ReportPriority priority;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
