package com.example.cacophony.security;

import com.example.cacophony.model.User;
import com.example.cacophony.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("USER NOT FOUND WITH USERNAME :: " + username));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPasswordHash(),
                user.getAuthorities()
        );
    }
}
