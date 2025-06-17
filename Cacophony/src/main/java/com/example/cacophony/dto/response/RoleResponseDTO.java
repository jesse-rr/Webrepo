package com.example.cacophony.dto;

import lombok.Builder;
import lombok.Data;
import java.util.Set;

@Data @Builder
public class RoleResponseDTO {
    private Long roleId;
    private String name;
    private String color;
    private long permissions;
    private Long communityId;
    private Set<String> memberUsernames;
}