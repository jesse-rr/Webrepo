package com.example.gymmanagement.dto;

public record MessageTemplateDTO(
        String name,
        String contentHTML,
        String contentImg,
        String subject
) {
}
