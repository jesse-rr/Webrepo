package com.example.gymmanagement.service;

import com.example.gymmanagement.dto.UserRequestDTO;
import com.example.gymmanagement.model.User;
import com.example.gymmanagement.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final GeneralMapper generalMapper;
    private final EmailService emailService;
    private final EncryptionService encryptionService;

    public void addUser(UserRequestDTO userDTO) {
        log.info("ADDING USER :: {}", userDTO);
        userRepository.save(generalMapper.toUser(
                userDTO,
                encryptionService.encryptPasswd(userDTO.password()))
        );

        emailService.sendMessage();
    }

    public void updateUser(char[] passwd, UserRequestDTO userDTO) {
        log.info("UPDATING USER :: {}", userDTO.email());
        User user = userRepository.findByEmail(userDTO.email())
                .orElseThrow(() -> new EntityNotFoundException("USER NOT FOUND WITH EMAIL :: "));
        if (!authenticateUser(user.getPassword(), passwd)) {
            throw new IllegalArgumentException("INVALID CREDENTIALS FOR LOGIN");
        }

        updateIfNotNull(userDTO.firstname(), user::setFirstname);
        updateIfNotNull(userDTO.lastname(), user::setLastname);
        updateIfNotNull(userDTO.cpf(), user::setCpf);
        updateIfNotNull(userDTO.phone(), user::setPhone);
        updateIfNotNull(userDTO.password(), p -> user.setPassword(encryptionService.encryptPasswd(p)));

        userRepository.save(user);
    }

    public void deleteUser(String email, char[] passwd) {
        log.info("DELETING USER :: {}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("USER NOT FOUND WITH EMAIL :: "+email));
        if (!authenticateUser(user.getPassword(), passwd)) {
            throw new IllegalArgumentException("INVALID CREDENTIALS FOR LOGIN");

        }
        user.setDeletionTimestamp(LocalDateTime.now().plusDays(7L));
        userRepository.save(user);

        emailService.sendMessage();
    }

    @Scheduled(cron = "0 0 * * *")
    private void deleteAllScheduledAccounts() {
        log.info("DELETING ALL SCHEDULED ACCOUNTS");
        userRepository.deleteAllScheduledUsers(LocalDateTime.now());
    }

    private boolean authenticateUser(String encPasswd, char[] passwd) {
        return encryptionService.validatePasswd(passwd, encPasswd);
    }

    private <T> void updateIfNotNull(T newValue, Consumer<T> setter) {
        Optional.ofNullable(newValue).ifPresent(setter);
    }
}
