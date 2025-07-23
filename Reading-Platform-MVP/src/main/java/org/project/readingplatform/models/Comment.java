package org.project.readingplatform.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity(name = "comment")
@Table(name = "comment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Size(max = 999, message = "REPORT MUST BE WITHIN 999 CHARS")
    private String content;
    @ElementCollection
    private Set<UUID> likes;
    @ElementCollection
    private Set<UUID> dislikes;

    @ManyToOne
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Report> reports;

    @OneToMany // TODO CHANGE NAME
    private List<Comment> comments;
}
