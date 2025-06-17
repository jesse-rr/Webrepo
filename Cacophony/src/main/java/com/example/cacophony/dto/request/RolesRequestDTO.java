package com.example.cacophony.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Random;

@Getter @Setter @ToString
public class RolesRequestDTO {
    @NotBlank @Size(max = 40)
    private String name;
    private String color;
    private long permissions;

    public RolesRequestDTO(String name, String color, long permissions) {
        this.name = name;
        this.permissions = permissions;
        if (color == null || color.isBlank()) {
            this.color = generateRandomColor();
        } else {
            this.color = color;
        }
    }

    public static String generateRandomColor() {
        Random random = new Random();
        return "#" + String.format("%02x",random.nextInt(256)) +
                String.format("%02x",random.nextInt(256)) +
                String.format("%02x",random.nextInt(256));
    }
}
