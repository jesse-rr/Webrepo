package org.project.readingplatform.controller;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.project.readingplatform.dto.UserDTO;
import org.project.readingplatform.service.AccountService;
import org.project.readingplatform.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auths")
public class GeneralController {

    private final UserService userService;
    private final AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody @Valid UserDTO userDTO
    ) throws MessagingException {
        userService.addUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location","/login")
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestParam @NotNull String login,
            @RequestParam @NotNull String password
    ) {
        return userService.login(login, password);
    }

    @PostMapping("/failure")
    public String failedLogin() {
        return "failure";
    }

    @GetMapping("/homepage")
    public String homepage() {
        return "homepage";
    }

    @PostMapping("/verify")
    public ResponseEntity<Void> verifyCode(
            @RequestParam String email,
            @RequestParam String code
    ) {
        accountService.verifyCode(email, code);
        return ResponseEntity.ok().build();
    }
}
