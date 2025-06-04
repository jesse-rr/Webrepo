package com.example.user.controller;

import com.example.user.dto.UserRequestDTO;
import com.example.user.model.User;
import com.example.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public void registerUser(
            @RequestBody UserRequestDTO request
    ) {
        userService.registerUser(request);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUserById(
            @PathVariable(value = "id") Long userId
    ) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PutMapping("/alter/{id}")
    public void alterUserById(
            @PathVariable(value = "id") Long userId,
            @RequestBody UserRequestDTO request
    ) {
        userService.alterUserById(userId, request);
    }
}
