package com.example.auctionhouse.service;

import com.example.auctionhouse.config.security.UserDetailsServiceImpl;
import com.example.auctionhouse.config.jwt.JwtUtil;
import com.example.auctionhouse.dto.UserDTO;
import com.example.auctionhouse.model.User;
import com.example.auctionhouse.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final AccountService accountService;
    private final NotificationService notificationService;
    private final GeneralMapper mapper;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final JwtUtil jwtUtil;

    public void register(UserDTO dto) throws MessagingException {
        log.info("REGISTERING USER WITH EMAIL :: {}",dto.email());
        User user = userRepository.save(mapper.toUser(dto, passwordEncoder.encode(dto.password())));
        accountService.createAccount(user);

        notificationService.sendEmailVerification(dto.email());
    }

    public String authenticate(String email, String password) {
        log.info("AUTHENTICATION OF USER :: {}",email);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        password
                )
        );
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(email);
        return jwtUtil.generateToken(userDetails);
    }

    public void alterUserPasswd(String email, String passwd) throws MessagingException {
        log.info("USER PASSWD ALTERATION IN PROGRESS TO EMAIL :: {}",email);
        User foundUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("NO USER FOUND WITH EMAIL :: "+email));
        if (foundUser.getEmail().equals(email) && foundUser.getPassword().equals(passwd)) {
            notificationService.sendPasswdAlterationNotification(email);
        }
    }
}
