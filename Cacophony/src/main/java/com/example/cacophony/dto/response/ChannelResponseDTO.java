package com.example.cacophony.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data @Builder
public class ChannelResponseDTO {
    private Long channelId;
    private String name;
    private long messageDelay;
    private Long communityId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}