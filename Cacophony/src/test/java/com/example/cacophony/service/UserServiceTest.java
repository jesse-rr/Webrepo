package com.example.cacophony.service;

import com.example.cacophony.dto.request.UserRequestDTO;
import com.example.cacophony.model.User;
import com.example.cacophony.model.helper.UserStatus;
import com.example.cacophony.repository.UserRepository;
import com.example.cacophony.security.JwtService;
import com.example.cacophony.service.helper.GeneralMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private GeneralMapper generalMapper;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private EmailService emailService;
    @Mock private JwtService jwtService;

    @InjectMocks
    private UserService userService;

    private final User testUser = User.builder()
            .userId(1L)
            .username("testuser")
            .passwordHash("encodedPass")
            .email("test@example.com")
            .displayName("Test User")
            .build();

    private final UserRequestDTO testDto = new UserRequestDTO(
            "testuser", "test@example.com", "password123", "Test User", null, null);

    @BeforeEach
    void setUp() {
        userService.pepper = "testPepper1234567890";
    }

    @Test
    void login_Success() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("password123testPepper1234567890", "encodedPass")).thenReturn(true);

        assertTrue(userService.login("testuser", "password123"));
        verify(jwtService).generateToken(testUser);
    }

    @Test
    void login_Fail() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertFalse(userService.login("testuser", "wrongpass"));
        verify(jwtService, never()).generateToken(any());
    }

    @Test
    void addUser_Success() {
        when(passwordEncoder.encode("password123testPepper1234567890")).thenReturn("encodedPass");
        when(generalMapper.toUser(testDto, "encodedPass")).thenReturn(testUser);

        userService.addUser(testDto);

        verify(userRepository).save(testUser);
        verify(emailService).sendEmail(any(), any());
    }

    @Test
    void updateUser_SensitiveInfo() {
        User updatedUser = User.builder().username("newuser").build();
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.encode("password123testPepper1234567890")).thenReturn("newEncodedPass");

        userService.updateUser(testDto, true);

        verify(userRepository).save(argThat(user ->
                user.getUsername().equals("testuser") &&
                        user.getPasswordHash().equals("newEncodedPass")
        ));
    }

    @Test
    void updateUser_NonSensitiveInfo() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        userService.updateUser(testDto, false);

        verify(userRepository).save(argThat(user ->
                user.getDisplayName().equals("Test User") &&
                        user.getBio() == null
        ));
    }

    @Test
    void updateUser_InvalidDisplayName() {
        UserRequestDTO badDto = new UserRequestDTO("testuser", "test@ex.com", "pass", "abc", null, null);
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        assertThrows(InvalidParameterException.class, () ->
                userService.updateUser(badDto, false));
    }

    @Test
    void scheduleDeletion() {
        when(userRepository.findEmailByUsername("testuser")).thenReturn("test@ex.com");

        userService.scheduleDeletion("testuser");

        verify(userRepository).scheduleDeletion(eq("testuser"), any(LocalDateTime.class));
    }

    @Test
    void getUserByUsername_Found() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        assertEquals(testUser, userService.getUserByUsername("testuser"));
    }

    @Test
    void getUserByUsername_NotFound() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () ->
                userService.getUserByUsername("unknown"));
    }

    @Test
    void encryptPassword() {
        when(passwordEncoder.encode("password123testPepper1234567890")).thenReturn("encoded");
        assertEquals("encoded", userService.encryptPasswd("password123"));
    }

    @Test
    void verifyPassword() {
        when(passwordEncoder.matches("password123testPepper1234567890", "encodedPass")).thenReturn(true);
        assertTrue(userService.verifyPasswd("password123", "encodedPass"));
    }

    @Test
    void changeUserStatus() {
        userService.changeUserStatus(UserStatus.OFFLINE, 1L);
        verify(userRepository).updateUserStatusById(UserStatus.OFFLINE, 1L);
    }

    @Test
    void isOldEnough() {
        when(userRepository.isOldEnought(eq("testuser"), any(LocalDateTime.class))).thenReturn(true);
        assertTrue(userService.isOldEnough("testuser"));
    }
}