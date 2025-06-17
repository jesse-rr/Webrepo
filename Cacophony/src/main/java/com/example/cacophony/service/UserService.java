package com.example.cacophony.service;

import com.example.cacophony.dto.request.EmailRequestDTO;
import com.example.cacophony.dto.request.UserRequestDTO;
import com.example.cacophony.dto.response.UserResponseDTO;
import com.example.cacophony.model.User;
import com.example.cacophony.model.helper.UserStatus;
import com.example.cacophony.repository.UserRepository;
import com.example.cacophony.security.JwtService;
import com.example.cacophony.service.helper.GeneralMapper;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;

import static com.example.cacophony.model.helper.TemplateNames.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final GeneralMapper generalMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtService jwtService;

    @Value(value = "${app.pepper}")
    public String pepper;

    @PostConstruct
    void verifyPepper() {
        if (pepper.length() < 16) {
            throw new InvalidParameterException("PEPPER LEGTH IS WRONG :: INVALID PEPPER");
        }
    }

    public boolean login(String username, String password) {
        User user = getUserByUsername(username);
        if (verifyPasswd(password, user.getPassword())) {
            jwtService.generateToken(user);
            return true;
        }
        return false;
    }

    public UserResponseDTO addUser(UserRequestDTO userRequestDTO) {
        log.info("ADDING USER :: {}",userRequestDTO);
        String passwordHash = encryptPasswd(userRequestDTO.getPassword());
        emailService.sendEmail(EMAIL_VERIFICATION.getName(), new EmailRequestDTO(userRequestDTO.getEmail(), userRequestDTO.getUsername()));

        return generalMapper.toUserResponseDTO(userRepository.save(generalMapper.toUser(
                userRequestDTO,
                passwordHash
        )));
    }

    @PreAuthorize("isAuthenticated()")
    public UserResponseDTO updateUser(UserRequestDTO userRequestDTO, boolean sensitiveInfo) {
        log.info("UPDATING USER :: {}",userRequestDTO.toString());
        User user = userRepository.findByUsername(userRequestDTO.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("USER NOT FOUND WITH USERNAME OF :: " + userRequestDTO.getUsername()));
        if (sensitiveInfo) {
            user.setUsername(userRequestDTO.getUsername());
            user.setPasswordHash(encryptPasswd(userRequestDTO.getPassword()));
        } else {
            if (userRequestDTO.getDisplayName().length() < 5) {
                throw new InvalidParameterException("INVALID DISPLAY NAME LENGTH OF < 5");
            }
            user.setBio(userRequestDTO.getBio());
            user.setDisplayName(userRequestDTO.getDisplayName());
            user.setProfileImg(userRequestDTO.getProfileImg());
        }
        emailService.sendEmail(ACCOUNT_UPDATE.getName(), new EmailRequestDTO(userRequestDTO.getEmail(), userRequestDTO.getUsername()));

        return generalMapper.toUserResponseDTO(userRepository.save(user));
    }

    public void scheduleDeletion(String username) {
        log.info("SCHEDULED DELETION OF :: {}",username);
        userRepository.scheduleDeletion(username, LocalDateTime.now().plusDays(7));

        emailService.sendEmail(ACCOUNT_DELETION.getName(), new EmailRequestDTO(userRepository.findEmailByUsername(username), username));
    }

    // ==== HELPER METHODS ====

    public void changeUserStatus(UserStatus status, Long userId) {
        userRepository.updateUserStatusById(status, userId);
    }

    public String getPasswordFromUsername(String username) {
        return userRepository.findPasswordByUsername(username);
    }


    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("USER NOT FOUND WITH USERNAME :: "+username));
    }

    public UserResponseDTO getUserDTOByUsername(String username) {
        return generalMapper.toUserResponseDTO(userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("USER NOT FOUND WITH USERNAME :: "+username)));
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("USER NOT FOUND WITH ID :: " + userId));

    }

    @Scheduled(cron = "0 0 3 * * ?") // daily - 3AM
    public void deleteAllScheduledAccounts() {
        userRepository.batchDeleteScheduledAccounts(LocalDateTime.now());
    }

    public boolean isOldEnough(String username) {
        return userRepository.isOldEnought(username, LocalDateTime.now().plusDays(7));
    }

    public String encryptPasswd(String passwd) {
        return passwordEncoder.encode(passwd + pepper);
    }

    public boolean verifyPasswd(String passwd, String encPasswd) {
        return passwordEncoder.matches(passwd+pepper, encPasswd);
    }}
