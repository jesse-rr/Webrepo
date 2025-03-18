package com.example.auctionhouse.controller;

import com.example.auctionhouse.dto.UserDTO;
import com.example.auctionhouse.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void> userRegister(
            @RequestBody @NotNull UserDTO dto
    ) throws MessagingException {
        userService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> userAuthentication(
            @RequestParam @NotNull String email,
            @RequestParam @NotNull String password
    ) {
        return ResponseEntity.ok(userService.authenticate(email,password));
    }
}
