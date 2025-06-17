package com.example.cacophony.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Builder
@Entity
@Table(name = "channels", uniqueConstraints = @UniqueConstraint(
        columnNames = { "name", "community_channel" })
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long channelId;

    @Column(nullable = false, length = 50)
    private String name;
    private long messageDelay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_channel", nullable = false)
    private Community community;

    @OneToMany(mappedBy = "channel", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Message> messages;
}
