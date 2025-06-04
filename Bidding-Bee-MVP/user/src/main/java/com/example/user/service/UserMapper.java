package com.example.user.service;

import com.example.user.dto.UserRequestDTO;
import com.example.user.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public User toUser(UserRequestDTO request) {
        return User.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .password(request.password())
                .phone(request.phone())
                .cpfCnpj(request.cpfCnpj())
                .address(request.address())
                .build();
    }
}
