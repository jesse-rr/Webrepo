package com.example.gymmanagement.model.enums;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    USER(Set.of("READ")),
    PERSONAL_TRAINER(Set.of("READ", "WRITE")),
    ADMIN(Set.of("READ", "WRITE", "DELETE", "MODERATE"));

    private final Set<String> authorities;

    Role(Set<String> authorities) {
        this.authorities = authorities;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
