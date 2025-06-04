package com.example.user.service;

import com.example.user.dto.UserRequestDTO;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User getUserById(Long userId) {
        log.info("RETRIEVING USER WITH ID :: {}",userId);
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("USER NOT FOUND WITH ID :: "+userId));
    }

    public void alterUserById(Long userId, UserRequestDTO request) {
        log.info("ALTERING USER BY ID :: {}", userId);
        User user = getUserById(userId);
        user.setFirstname(request.firstname());
        user.setLastname(request.lastname());
        user.setEmail(request.email());
        user.setAddress(request.address());
        userRepository.save(user);
    }

    public void registerUser(UserRequestDTO request) {
        log.info("REGISTERING USER DTO :: {}",request);
        userRepository.save(userMapper.toUser(request));
    }
}
