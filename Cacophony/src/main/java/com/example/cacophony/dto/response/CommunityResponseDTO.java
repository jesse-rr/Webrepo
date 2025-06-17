package com.example.cacophony.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data @Builder
public class CommunityResponseDTO {
    private Long communityId;
    private String name;
    private String description;
    private String iconImg;
    private String bannerImg;
    private String ownerUsername;
    private Set<Long> channelIds;
    private Set<String> memberUsernames;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}