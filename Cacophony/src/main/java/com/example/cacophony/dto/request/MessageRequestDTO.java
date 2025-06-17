package com.example.cacophony.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@AllArgsConstructor
public class MessageRequestDTO {
    @NotBlank
    private String content;
    @NotBlank
    private String username;
    private Long replyToId;
}
