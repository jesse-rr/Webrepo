package org.project.readingplatform.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.project.readingplatform.dto.ChapterDTO;

import java.util.List;

@Entity(name = "chapter")
@Table(name = "chapter")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chapterId;

    private String title;
    private String content;
    @Column(nullable = false)
    private int chapterNumber;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @OneToMany(mappedBy = "chapter", fetch = FetchType.LAZY)
    private List<Comment> comments;
}
