package com.example.user.dto;

import com.example.user.model.Address;

public record UserRequestDTO(
        String firstname,
        String lastname,
        String email,
        String password,
        String cpfCnpj,
        String phone,
        Address address
) {
}
