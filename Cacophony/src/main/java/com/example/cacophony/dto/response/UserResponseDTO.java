package com.example.cacophony.dto.response;

import com.example.cacophony.model.helper.UserStatus;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data @Builder
public class UserResponseDTO {
    private Long userId;
    private String username;
    private String email;
    private String displayName;
    private String profileImg;
    private UserStatus status;
    private String bio;
    private Set<Long> communityIds;
    private Set<String> roleNames;
    private LocalDateTime joinedAt;
}