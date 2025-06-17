package com.example.cacophony.dto.request;

import jakarta.persistence.Column;
import lombok.*;

@Getter @Setter @ToString
@AllArgsConstructor
public class CommunityRequestDTO {
    @Column(nullable = false)
    private String name;
    private String description;
    @Column(name = "icon_image")
    private String iconImg;
    @Column(name = "banner_image")
    private String bannerImg;


}
