package com.example.gymmanagement.service;

import com.example.gymmanagement.dto.UserRequestDTO;
import com.example.gymmanagement.model.User;
import com.example.gymmanagement.model.enums.Role;
import org.springframework.stereotype.Service;

@Service
public class GeneralMapper {

    public User toUser(UserRequestDTO userDTO, String encPasswd) {
        return User.builder()
                .firstname(userDTO.firstname())
                .lastname(userDTO.lastname())
                .email(userDTO.email())
                .cpf(userDTO.cpf())
                .phone(userDTO.phone())
                .password(encPasswd)
                .role(Role.USER)
                .build();
    }
}
