package com.example.cacophony.controlller;


import com.example.cacophony.dto.request.UserRequestDTO;
import com.example.cacophony.dto.response.UserResponseDTO;
import com.example.cacophony.model.User;
import com.example.cacophony.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestParam String username,
            @RequestParam String password
    ) {
        if (userService.login(username, password)) {
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.badRequest().body("Invalid credentials");
    }

    @PostMapping
    public ResponseEntity<Void> createUser(
            @RequestBody @Valid UserRequestDTO userRequestDTO
    ) {
        userService.addUser(userRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{username}")
    public ResponseEntity<Void> updateUser(
            @PathVariable(name = "username") String username,
            @RequestBody @Valid UserRequestDTO userRequestDTO,
            @RequestParam(required = false) boolean sensitiveInfo
    ) {
        userService.updateUser(userRequestDTO, sensitiveInfo);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> scheduleDeletion(@PathVariable(name = "username") String username) {
        userService.scheduleDeletion(username);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable(name = "username") String username) {
        return ResponseEntity.ok(userService.getUserDTOByUsername(username));
    }
}
