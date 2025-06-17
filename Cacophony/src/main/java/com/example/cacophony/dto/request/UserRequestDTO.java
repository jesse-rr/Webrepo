package com.example.cacophony.dto.request;

import com.example.cacophony.annotation.ValidPassword;
import com.example.cacophony.annotation.ValidUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@AllArgsConstructor
public class UserRequestDTO {
    @NotBlank @ValidUsername
    private String username;
    @NotBlank @Email
    private String email;
    @NotBlank @Size(min = 8) @ValidPassword
    private String password;

    @Size(max = 50)
    private String displayName;
    @Size(max = 255)
    private String profileImg;
    @Size(max = 500)
    private String bio;
}
