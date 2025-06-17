package com.example.cacophony.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data @Builder
public class MessageResponseDTO {
    private Long messageId;
    private String content;
    private LocalDateTime timestamp;
    private boolean edited;
    private boolean pinned;
    private Long replyToId;
    private String authorUsername;
    private Long channelId;
    private Set<Long> replyIds;
}