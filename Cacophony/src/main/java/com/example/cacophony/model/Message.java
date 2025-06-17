package com.example.cacophony.model;

import com.example.cacophony.model.helper.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Entity
@Table(name = "messages")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @Column(nullable = false, columnDefinition = "TEXT", length = 2000)
    private String content;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();
    @Column(nullable = false)
    private boolean edited = false;
    @Column(nullable = false)
    private boolean pinned = false;

    // TODO Attachments/Reactions

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_to_id")
    private Message replyTo;

    @OneToMany(mappedBy = "replyTo")
    private Set<Message> replies;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_message", nullable = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_message", nullable = false)
    private Channel channel;

}
