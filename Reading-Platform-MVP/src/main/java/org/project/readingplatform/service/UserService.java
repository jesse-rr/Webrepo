package org.project.readingplatform.service;

import io.jsonwebtoken.security.Password;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.readingplatform.dto.UserDTO;
import org.project.readingplatform.models.User;
import org.project.readingplatform.repository.UserRepository;
import org.project.readingplatform.service.mapper.GeneralMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AccountService accountService;
    private final GeneralMapper mapper;
    private final JwtService jwtService;

    public void addUser(UserDTO request) throws MessagingException {
        log.info("ADDING USER");
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!request.password().equals(request.matchingPassword())) {
            throw new RuntimeException("USER PASSWORD`S DO NOT MATCH");
        }
        if (userRepository.findByLogin(request.login()) != null) {
            throw new RuntimeException("LOGIN ALREADY TAKEN");
        }

        var user = userRepository.save(mapper.toUser(
                request,
                passwordEncoder.encode(request.password()))
        );
        accountService.addAccount(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("USER COULD NOT BE FOUND WITH EMAIL :: <%s>",username));
        }
        return user;
    }

    public ResponseEntity<String> login(String login, String password) {
        UserDetails userDetails = this.loadUserByUsername(login);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (userDetails != null && passwordEncoder.matches(password,userDetails.getPassword())) {
            String jwt = jwtService.generateToken(userDetails);
            return ResponseEntity.status(HttpStatus.ACCEPTED).header("Location","/homepage").body(jwt);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
        }
    }
}
